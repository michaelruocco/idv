package uk.co.mruoc.idv.lockout.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InMemoryLockoutPolicyDaoTest {

    private final LockoutPolicyParametersConverter parametersConverter = mock(LockoutPolicyParametersConverter.class);

    private final LockoutPolicyDao dao = new InMemoryLockoutPolicyDao(parametersConverter);

    @Test
    void shouldReturnEmptyOptionalIfPolicyNotFoundById() {
        final UUID id = UUID.randomUUID();

        assertThat(dao.load(id)).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalIfPolicyNotFoundByLockoutRequest() {
        final LockoutRequest request = mock(LockoutRequest.class);

        assertThat(dao.load(request)).isEmpty();
    }

    @Test
    void shouldLoadSavedPolicyById() {
        final UUID id = UUID.randomUUID();
        final LockoutPolicy policy = createSavedPolicyWithId(id);
        dao.save(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadSavedPolicyByLockoutRequest() {
        final LockoutRequest request = mock(LockoutRequest.class);
        final LockoutPolicy policy = createSavedPolicyThatAppliesTo(request);
        dao.save(policy);

        final Optional<LockoutPolicy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadAllSavedPolicies() {
        final LockoutPolicy policy1 = createSavedPolicyWithId(UUID.randomUUID());
        final LockoutPolicy policy2 = createSavedPolicyWithId(UUID.randomUUID());
        dao.save(policy1);
        dao.save(policy2);

        final Collection<LockoutPolicy> policies = dao.load();

        assertThat(policies).containsExactlyInAnyOrder(policy1, policy2);
    }

    private LockoutPolicy createSavedPolicyThatAppliesTo(final LockoutRequest request) {
        final LockoutPolicy policy = createSavedPolicyWithId(UUID.randomUUID());
        given(policy.appliesTo(request)).willReturn(true);
        return policy;
    }

    private LockoutPolicy createSavedPolicyWithId(final UUID id) {
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);
        given(policy.getParameters()).willReturn(parameters);
        given(parameters.getId()).willReturn(id);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);
        return policy;
    }

}
