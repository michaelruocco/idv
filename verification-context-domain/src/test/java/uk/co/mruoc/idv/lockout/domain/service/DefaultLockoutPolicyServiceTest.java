package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyService.LockoutPolicyNotFoundException;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyServiceTest {

    private final LockoutPolicyDao dao = mock(LockoutPolicyDao.class);

    private final LockoutPolicy policy = mock(LockoutPolicy.class);

    private final LockoutPolicyService service = new DefaultLockoutPolicyService(dao);

    @BeforeEach
    void setUp() {
        service.addPolicy(policy);
    }

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
        given(policy.reset(request.getAttempts())).willReturn(expectedAttempts);

        final VerificationAttempts attempts = service.resetAttempts(request);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    private static RecordAttemptRequest buildRecordAttemptRequest() {
        return RecordAttemptRequest.builder()
                .context(new FakeVerificationContext())
                .result(new FakeVerificationResultSuccessful("method-name"))
                .build();
    }

}
