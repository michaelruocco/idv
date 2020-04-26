package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.usecases.lockout.attempt.LockoutAttemptRecorder;
import uk.co.idv.domain.usecases.lockout.state.DefaultLoadLockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateLoader;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateResetter;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateValidator;

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
        final LockoutState expectedState = new FakeHardLockoutState();
        given(attemptRecorder.recordAttempt(request)).willReturn(expectedState);

        final LockoutState state = service.recordAttempt(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldLoadState() {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();
        final LockoutState expectedState = new FakeHardLockoutState();
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
    void shouldResetStateThenLoadLockoutState() {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();
        final LockoutState expectedState = new FakeHardLockoutState();
        given(stateLoader.load(request)).willReturn(expectedState);

        final LockoutState state = service.resetState(request);

        verify(stateResetter).reset(request);
        assertThat(state).isEqualTo(expectedState);
    }

}
