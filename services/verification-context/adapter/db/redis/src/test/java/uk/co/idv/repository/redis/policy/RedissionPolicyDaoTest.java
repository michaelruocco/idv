package uk.co.idv.repository.redis.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.usecases.policy.PolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RedissionPolicyDaoTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final PolicyDao<Policy> dao = new RedissionPolicyDao<>(new HashMap<>(), jsonConverter, Policy.class);

    @Test
    void shouldReturnEmptyOptionalIfPolicyNotFoundById() {
        final UUID id = UUID.randomUUID();

        assertThat(dao.load(id)).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalIfPolicyNotFoundByPolicyRequest() {
        final PolicyRequest request = mock(PolicyRequest.class);

        assertThat(dao.load(request)).isEmpty();
    }

    @Test
    void shouldLoadSavedPolicyById() {
        final UUID id = UUID.randomUUID();
        final Policy policy = createSavedPolicyWithId(id);
        givenPolicyIsSerializable(policy);
        dao.save(policy);

        final Optional<Policy> loadedPolicy = dao.load(id);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadSavedPolicyByLockoutRequest() {
        final LockoutRequest request = mock(LockoutRequest.class);
        final Policy policy = createSavedPolicyThatAppliesTo(request);
        givenPolicyIsSerializable(policy);
        dao.save(policy);

        final Collection<Policy> loadedPolicy = dao.load(request);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadAllSavedPolicies() {
        final Policy policy1 = createSavedPolicyWithId(UUID.randomUUID());
        final Policy policy2 = createSavedPolicyWithId(UUID.randomUUID());
        givenPolicyIsSerializable(policy1);
        givenPolicyIsSerializable(policy2);
        dao.save(policy1);
        dao.save(policy2);

        final Collection<Policy> policies = dao.load();

        assertThat(policies).containsExactlyInAnyOrder(policy1, policy2);
    }

    private Policy createSavedPolicyThatAppliesTo(final PolicyRequest request) {
        final Policy policy = createSavedPolicyWithId(UUID.randomUUID());
        given(policy.appliesTo(request)).willReturn(true);
        return policy;
    }

    private Policy createSavedPolicyWithId(final UUID id) {
        final Policy policy = mock(Policy.class);
        given(policy.getId()).willReturn(id);
        return policy;
    }

    private void givenPolicyIsSerializable(final Policy policy) {
        final String json = String.format("{\"id\":\"%s\"}", policy.getId());
        given(jsonConverter.toJson(policy)).willReturn(json);
        given(jsonConverter.toObject(json, Policy.class)).willReturn(policy);
    }

}
