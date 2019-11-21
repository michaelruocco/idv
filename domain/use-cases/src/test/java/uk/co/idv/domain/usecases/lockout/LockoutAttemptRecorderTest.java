package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttemptFailed;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttemptSuccessful;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutAttemptRecorderTest {

    private final RecordAttemptRequestConverter requestConverter = mock(RecordAttemptRequestConverter.class);
    private final LockoutPolicyService policyService = mock(LockoutPolicyService.class);
    private final LockoutStateLoader stateLoader = mock(LockoutStateLoader.class);
    private final LockoutStateResetter stateResetter = mock(LockoutStateResetter.class);
    private final VerificationAttemptPersister attemptPersister = mock(VerificationAttemptPersister.class);

    private final LockoutAttemptRecorder recorder = LockoutAttemptRecorder.builder()
            .requestConverter(requestConverter)
            .policyService(policyService)
            .stateLoader(stateLoader)
            .stateResetter(stateResetter)
            .attemptPersister(attemptPersister)
            .build();

    @Test
    void shouldReturnLoadedLockoutStateIfShouldNotRecordAttempt() {
        final RecordAttemptRequest request = toRequest(new FakeVerificationResultSuccessful("method-name"));
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();
        final LockoutState loadedState = new FakeHardLockoutState();
        given(requestConverter.toAttempt(request)).willReturn(attempt);
        given(policyService.shouldRecordAttempt(request)).willReturn(false);
        given(stateLoader.load(attempt)).willReturn(loadedState);

        final LockoutState state = recorder.recordAttempt(request);

        assertThat(state).isEqualTo(loadedState);
    }

    @Test
    void shouldResetLockoutStateIfShouldRecordSuccessfulAttempt() {
        final RecordAttemptRequest request = toRequest(new FakeVerificationResultSuccessful("method-name"));
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();
        final LockoutState loadedState = new FakeHardLockoutState();
        given(requestConverter.toAttempt(request)).willReturn(attempt);
        given(policyService.shouldRecordAttempt(request)).willReturn(true);
        given(stateLoader.load(attempt)).willReturn(loadedState);

        final LockoutState state = recorder.recordAttempt(request);

        verify(stateResetter).reset(attempt);
        assertThat(state).isEqualTo(loadedState);
    }

    @Test
    void shouldRecordFailedAttemptIfShouldRecordFailedAttempt() {
        final RecordAttemptRequest request = toRequest(new FakeVerificationResultFailed("method-name"));
        final VerificationAttempt attempt = new FakeVerificationAttemptFailed();
        final LockoutState loadedState = new FakeHardLockoutState();
        given(requestConverter.toAttempt(request)).willReturn(attempt);
        given(policyService.shouldRecordAttempt(request)).willReturn(true);
        given(stateLoader.load(attempt)).willReturn(loadedState);

        final LockoutState state = recorder.recordAttempt(request);

        verify(attemptPersister).persist(attempt);
        assertThat(state).isEqualTo(loadedState);
    }

    private static RecordAttemptRequest toRequest(final VerificationResult result) {
        return RecordAttemptRequest.builder()
                .result(result)
                .context(new FakeVerificationContext())
                .build();
    }

}
