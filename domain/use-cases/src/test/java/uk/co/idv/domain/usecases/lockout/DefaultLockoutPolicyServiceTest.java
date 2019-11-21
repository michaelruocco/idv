package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService.LockoutPolicyNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultLockoutPolicyServiceTest {

    private final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);
    private final LockoutPolicy policy = mock(LockoutPolicy.class);

    private final LockoutPolicyDao dao = mock(LockoutPolicyDao.class);

    private final LockoutPolicyService service = new DefaultLockoutPolicyService(dao);

    @Test
    void shouldThrowExceptionIfNoPolicesThatApplyToRecordAttemptRequest() {
        final RecordAttemptRequest request = buildRecordAttemptRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.shouldRecordAttempt(request));

        assertThat(error).isInstanceOf(LockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToRecordAttemptRequest() {
        final RecordAttemptRequest request = buildRecordAttemptRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.shouldRecordAttempt(request));

        final LockoutPolicyNotFoundException exception = (LockoutPolicyNotFoundException) error;
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

        assertThat(error).isInstanceOf(LockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToCalculateStateRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.calculateState(request));

        final LockoutPolicyNotFoundException exception = (LockoutPolicyNotFoundException) error;
        assertThat(exception.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldCalculateStateFromPolicy() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.of(policy));
        final LockoutState expectedState = new FakeHardLockoutState();
        given(policy.getStateCalculator()).willReturn(stateCalculator);
        given(stateCalculator.calculate(request)).willReturn(expectedState);

        final LockoutState state = service.calculateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldThrowExceptionIfNoPolicesThatApplyToResetAttemptsRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.resetAttempts(request));

        assertThat(error).isInstanceOf(LockoutPolicyNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWithRequestIfNoPolicesThatApplyToResetAttemptsRequest() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.resetAttempts(request));

        final LockoutPolicyNotFoundException exception = (LockoutPolicyNotFoundException) error;
        assertThat(exception.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldResetAttemptsUsingPolicy() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        given(dao.load(request)).willReturn(Optional.of(policy));
        final VerificationAttempts expectedAttempts = new FakeVerificationAttempts();
        given(policy.reset(request.getAttempts(), request)).willReturn(expectedAttempts);

        final VerificationAttempts attempts = service.resetAttempts(request);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldSavePolicy() {
        service.savePolicy(policy);

        verify(dao).save(policy);
    }

    @Test
    void shouldSaveMultiplePolicies() {
        final LockoutPolicy policy1 = mock(LockoutPolicy.class);

        service.savePolicies(Arrays.asList(policy, policy1));

        verify(dao).save(policy);
        verify(dao).save(policy1);
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
