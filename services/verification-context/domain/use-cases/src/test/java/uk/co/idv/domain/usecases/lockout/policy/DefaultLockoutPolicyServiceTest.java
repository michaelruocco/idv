package uk.co.idv.domain.usecases.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequestMother;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.policy.PolicyService.PoliciesAlreadyExistException;
import uk.co.idv.domain.usecases.policy.PolicyService.PolicyNotFoundException;
import uk.co.idv.domain.usecases.policy.PolicyService.RequestedPolicyNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultLockoutPolicyServiceTest {

    private final LockoutPolicyDao dao = mock(LockoutPolicyDao.class);
    private final PolicyLevelConverter policyLevelConverter = mock(PolicyLevelConverter.class);
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler = mock(MultipleLockoutPoliciesHandler.class);

    private final LockoutPolicy policy = mock(LockoutPolicy.class);

    private final LockoutPolicyService service = new DefaultLockoutPolicyService(dao, multiplePoliciesHandler, policyLevelConverter);

    @Test
    void shouldCreatePolicyIfNoPoliciesAlreadyExistForSameLevel() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        given(policy.getLevel()).willReturn(level);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.emptyList());

        service.create(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldThrowExceptionIfPolicyAlreadyExistForSameLevel() {
        final PolicyRequest request = mock(PolicyRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.getLevel()).willReturn(level);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.singleton(policy));

        final Throwable error = catchThrowable(() -> service.create(policy));

        assertThat(error)
                .isInstanceOf(PoliciesAlreadyExistException.class)
                .hasMessage(String.format("policies %s already exist for same lockout level", id.toString()));
    }

    @Test
    void shouldCreateMultiplePolicies() {
        final LockoutPolicy policy1 = mock(LockoutPolicy.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final PolicyRequest request = mock(PolicyRequest.class);
        given(policy.getLevel()).willReturn(level);
        given(policy1.getLevel()).willReturn(level);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Collections.emptyList());

        service.create(Arrays.asList(policy, policy1));

        verify(dao).save(policy);
        verify(dao).save(policy1);
    }

    @Test
    void shouldThrowExceptionIfPolicyDoesNotAlreadyExist() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldUpdatePolicy() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.of(policy));

        service.update(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldReturnAllPolicies() {
        final Collection<LockoutPolicy> expectedPolicies = Collections.singleton(policy);
        given(dao.load()).willReturn(expectedPolicies);

        final Collection<LockoutPolicy> policies = service.loadAll();

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.load(id));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnPolicyById() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.of(policy));

        final LockoutPolicy loadedPolicy = service.load(id);

        assertThat(loadedPolicy).isEqualTo(policy);
    }

    @Test
    void shouldLoadPolicyApplicableToRequest() {
        final LockoutRequest request = mock(LockoutRequest.class);
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        final List<LockoutPolicy> policies = Collections.singletonList(policy);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.of(policy));
        given(dao.load(request)).willReturn(policies);

        final LockoutPolicy loadedPolicy = service.load(request);

        assertThat(loadedPolicy).isEqualTo(policy);
    }

    @Test
    void shouldThrowExceptionIfApplicablePolicyNotFound() {
        final LockoutRequest request = LockoutRequestMother.fake();
        final PolicyLevel level = mock(PolicyLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.appliesTo(request)).willReturn(true);
        given(policyLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        final List<LockoutPolicy> policies = Collections.singletonList(policy);
        given(multiplePoliciesHandler.extractPolicy(policies)).willReturn(Optional.empty());
        given(dao.load(request)).willReturn(policies);

        final Throwable error = catchThrowable(() -> service.load(request));

        assertThat(error)
                .isInstanceOf(RequestedPolicyNotFoundException.class)
                .hasMessage(toExpectedMessage(request));
    }

    private static String toExpectedMessage(final LockoutRequest request) {
        return String.format("channelId %s activity %s aliasType %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAlias().getType());
    }

}
