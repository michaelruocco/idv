package uk.co.mruoc.idv.lockout.dao;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class InMemoryLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyParametersConverter parametersConverter;
    private final Map<UUID, LockoutPolicyParameters> policies = new HashMap<>();

    @Override
    public void save(final LockoutPolicy policy) {
        final LockoutPolicyParameters parameters = policy.getParameters();
        policies.put(parameters.getId(), parameters);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        return Optional.ofNullable(policies.get(id))
                .map(parametersConverter::toPolicy);
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        return paramsAsPolicyStream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst();
    }

    @Override
    public Collection<LockoutPolicy> load() {
        return paramsAsPolicyStream().collect(Collectors.toList());
    }

    private Stream<LockoutPolicy> paramsAsPolicyStream() {
        return policies.values().stream().map(parametersConverter::toPolicy);
    }

}