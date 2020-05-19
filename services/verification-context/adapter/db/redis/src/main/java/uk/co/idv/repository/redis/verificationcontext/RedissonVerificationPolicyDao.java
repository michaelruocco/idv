package uk.co.idv.repository.redis.verificationcontext;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RedissonVerificationPolicyDao implements VerificationPolicyDao {

    private final JsonConverter jsonConverter;
    private final RMap<UUID, String> policies;

    @Override
    public void save(final VerificationPolicy policy) {
        final String json = jsonConverter.toJson(policy);
        policies.put(policy.getId(), json);
    }

    @Override
    public Optional<VerificationPolicy> load(final UUID id) {
        final Optional<String> json = Optional.ofNullable(policies.get(id));
        return json.map(this::toPolicy);
    }

    @Override
    public Collection<VerificationPolicy> load(final PolicyRequest request) {
        return load().stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<VerificationPolicy> load() {
        return policies.values()
                .stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public void clear() {
        policies.clear();
    }

    private VerificationPolicy toPolicy(final String json) {
        return jsonConverter.toObject(json, VerificationPolicy.class);
    }

}
