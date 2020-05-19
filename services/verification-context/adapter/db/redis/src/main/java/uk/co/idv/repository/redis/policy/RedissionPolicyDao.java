package uk.co.idv.repository.redis.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.usecases.policy.PolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RedissionPolicyDao<T extends Policy> implements PolicyDao<T> {

    private final Map<UUID, String> policies;
    private final JsonConverter jsonConverter;
    private final Class<T> type;

    @Override
    public void save(final T policy) {
        final String json = jsonConverter.toJson(policy);
        policies.put(policy.getId(), json);
    }

    @Override
    public Optional<T> load(final UUID id) {
        return Optional.ofNullable(policies.get(id)).map(this::toPolicy);
    }

    @Override
    public Collection<T> load(final PolicyRequest request) {
        return load().stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<T> load() {
        return policies.values().stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    private T toPolicy(final String json) {
        return jsonConverter.toObject(json, type);
    }

}
