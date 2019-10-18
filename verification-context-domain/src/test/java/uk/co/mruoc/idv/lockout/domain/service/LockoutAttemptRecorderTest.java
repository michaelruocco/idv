package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttemptSuccessful;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

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
        final LockoutState loadedState = new FakeLockoutStateMaxAttempts();
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
        final LockoutState loadedState = new FakeLockoutStateMaxAttempts();
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
        final LockoutState loadedState = new FakeLockoutStateMaxAttempts();
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
