package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.state.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.state.FakeLockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParametersMother;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
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

    private final LockoutPolicy policy = mock(LockoutPolicy.class);

    private final LockoutPolicyParametersConverter parametersConverter = mock(LockoutPolicyParametersConverter.class);
    private final LockoutPolicyDao dao = mock(LockoutPolicyDao.class);

    private final LockoutPolicyService service = DefaultLockoutPolicyService.builder()
            .parametersConverter(parametersConverter)
            .dao(dao)
            .build();

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
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        given(policy.calculateLockoutState(request)).willReturn(expectedState);

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
        final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);

        service.addPolicy(parameters);

        verify(dao).save(policy);
    }

    @Test
    void shouldSaveMultiplePolicies() {
        final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);
        given(parametersConverter.toPolicy(parameters)).willReturn(policy);
        final LockoutPolicy policy1 = mock(LockoutPolicy.class);
        final DefaultLockoutPolicyParameters parameters1 = mock(DefaultLockoutPolicyParameters.class);
        given(parametersConverter.toPolicy(parameters1)).willReturn(policy1);

        service.addPolicies(Arrays.asList(parameters, parameters1));

        verify(dao).save(policy);
        verify(dao).save(policy1);
    }

    @Test
    void shouldReturnAllPolicies() {
        final Collection<LockoutPolicy> policies = Collections.singleton(policy);
        given(dao.load()).willReturn(policies);
        final LockoutPolicyParameters expectedParameters = LockoutPolicyParametersMother.maxAttempts();
        given(policy.getParameters()).willReturn(expectedParameters);

        final Collection<LockoutPolicyParameters> policyParameters = service.loadPolicies();

        assertThat(policyParameters).containsExactly(expectedParameters);
    }

    private static RecordAttemptRequest buildRecordAttemptRequest() {
        return RecordAttemptRequest.builder()
                .context(new FakeVerificationContext())
                .result(new FakeVerificationResultSuccessful("method-name"))
                .build();
    }

}
