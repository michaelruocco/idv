package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultLockoutServiceTest {

    private final LockoutAttemptRecorder attemptRecorder = mock(LockoutAttemptRecorder.class);
    private final LockoutStateLoader stateLoader = mock(LockoutStateLoader.class);
    private final LockoutStateValidator stateValidator = mock(LockoutStateValidator.class);
    private final LockoutStateResetter stateResetter = mock(LockoutStateResetter.class);

    private final LockoutService service = DefaultLockoutService.builder()
            .attemptRecorder(attemptRecorder)
            .stateLoader(stateLoader)
            .stateValidator(stateValidator)
            .stateResetter(stateResetter)
            .build();

    @Test
    void shouldRecordAttempt() {
        final RecordAttemptRequest request = RecordAttemptRequest.builder().build();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        given(attemptRecorder.recordAttempt(request)).willReturn(expectedState);

        final LockoutState state = service.recordAttempt(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldLoadState() {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        given(stateLoader.load(request)).willReturn(expectedState);

        final LockoutState state = service.loadState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldValidateState() {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();

        service.validateState(request);

        final ArgumentCaptor<LockoutStateRequest> captor = ArgumentCaptor.forClass(LockoutStateRequest.class);
        verify(stateValidator).validateState(captor.capture());
        assertThat(captor.getValue()).isEqualTo(request);
    }

    @Test
    void shouldResetState() {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        given(stateResetter.reset(request)).willReturn(expectedState);

        final LockoutState state = service.resetState(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
