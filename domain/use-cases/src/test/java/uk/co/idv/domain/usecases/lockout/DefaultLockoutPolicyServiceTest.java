package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelConverter;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService.LockoutPoliciesAlreadyExistException;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService.LockoutPolicyNotFoundException;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService.RequestedLockoutPolicyNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultLockoutPolicyServiceTest {

    private final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);
    private final LockoutPolicy policy = mock(LockoutPolicy.class);
    private final LockoutLevelConverter lockoutLevelConverter = mock(LockoutLevelConverter.class);

    private final LockoutPolicyDao dao = mock(LockoutPolicyDao.class);

    private final LockoutPolicyService service = new DefaultLockoutPolicyService(dao, lockoutLevelConverter);

    @Test
    void shouldThrowExceptionIfNoPolicesThatApplyToRecordAttemptRequest() {
        final RecordAttemptRequest request = buildRecordAttemptRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.shouldRecordAttempt(request));

        assertThat(error).isInstanceOf(RequestedLockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToRecordAttemptRequest() {
        final RecordAttemptRequest request = buildRecordAttemptRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.shouldRecordAttempt(request));

        final RequestedLockoutPolicyNotFoundException exception = (RequestedLockoutPolicyNotFoundException) error;
        assertThat(exception.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldReturnRecordAttemptFromPolicy() {
        final RecordAttemptRequest request = buildRecordAttemptRequest();
        given(dao.load(request)).willReturn(Optional.of(policy));
        given(policy.shouldRecordAttempt(request)).willReturn(true);

        final boolean result = service.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldThrowExceptionIfNoPolicesThatApplyToCalculateStateRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.calculateState(request));

        assertThat(error).isInstanceOf(RequestedLockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToCalculateStateRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.calculateState(request));

        final RequestedLockoutPolicyNotFoundException exception = (RequestedLockoutPolicyNotFoundException) error;
        assertThat(exception.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldCalculateStateFromPolicy() {
        final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        given(request.getAttempts()).willReturn(attempts);
        given(dao.load(request)).willReturn(Optional.of(policy));
        final VerificationAttempts applicableAttempts = VerificationAttemptsMother.oneAttempt();
        given(policy.filterApplicableAttempts(attempts, request)).willReturn(applicableAttempts);
        final CalculateLockoutStateRequest updatedRequest = mock(CalculateLockoutStateRequest.class);
        given(request.updateAttempts(applicableAttempts)).willReturn(updatedRequest);
        given(policy.getStateCalculator()).willReturn(stateCalculator);
        final LockoutState expectedState = new FakeHardLockoutState();
        given(stateCalculator.calculate(updatedRequest)).willReturn(expectedState);

        final LockoutState state = service.calculateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldThrowExceptionIfNoPolicesThatApplyToResetAttemptsRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.resetAttempts(request));

        assertThat(error).isInstanceOf(RequestedLockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToResetAttemptsRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.resetAttempts(request));

        final RequestedLockoutPolicyNotFoundException exception = (RequestedLockoutPolicyNotFoundException) error;
        assertThat(exception.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldResetAttemptsUsingPolicy() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.of(policy));
        final VerificationAttempts expectedAttempts = VerificationAttemptsMother.oneAttempt();
        given(policy.reset(request.getAttempts(), request)).willReturn(expectedAttempts);

        final VerificationAttempts attempts = service.resetAttempts(request);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldCreatePolicyIfNoPoliciesAlreadyExistForSameLevel() {
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        final LockoutLevel level = mock(LockoutLevel.class);
        given(policy.getLockoutLevel()).willReturn(level);
        given(lockoutLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Optional.empty());

        service.createPolicy(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldThrowExceptionIfPolicyAlreadyExistForSameLevel() {
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        final LockoutLevel level = mock(LockoutLevel.class);
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(policy.getLockoutLevel()).willReturn(level);
        given(lockoutLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Optional.of(policy));

        final Throwable error = catchThrowable(() -> service.createPolicy(policy));

        assertThat(error)
                .isInstanceOf(LockoutPoliciesAlreadyExistException.class)
                .hasMessage(String.format("lockout policies %s already exist for same lockout level", id.toString()));
    }

    @Test
    void shouldCreateMultiplePolicies() {
        final LockoutPolicy policy1 = mock(LockoutPolicy.class);
        final LockoutLevel level = mock(LockoutLevel.class);
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(policy.getLockoutLevel()).willReturn(level);
        given(policy1.getLockoutLevel()).willReturn(level);
        given(lockoutLevelConverter.toPolicyRequests(level)).willReturn(Collections.singleton(request));
        given(dao.load(request)).willReturn(Optional.empty());

        service.createPolicies(Arrays.asList(policy, policy1));

        verify(dao).save(policy);
        verify(dao).save(policy1);
    }

    @Test
    void shouldThrowExceptionIfPolicyDoesNotAlreadyExist() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.updatePolicy(policy));

        assertThat(error)
                .isInstanceOf(LockoutPolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldUpdatePolicy() {
        final UUID id = UUID.randomUUID();
        given(policy.getId()).willReturn(id);
        given(dao.load(id)).willReturn(Optional.of(policy));

        service.updatePolicy(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldReturnAllPolicies() {
        final Collection<LockoutPolicy> expectedPolicies = Collections.singleton(policy);
        given(dao.load()).willReturn(expectedPolicies);

        final Collection<LockoutPolicy> policies = service.loadPolicies();

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    private static RecordAttemptRequest buildRecordAttemptRequest() {
        return RecordAttemptRequest.builder()
                .context(new FakeVerificationContext())
                .result(new FakeVerificationResultSuccessful("method-name"))
                .build();
    }

}
