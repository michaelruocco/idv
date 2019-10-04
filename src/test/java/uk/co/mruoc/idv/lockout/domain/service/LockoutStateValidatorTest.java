package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator.LockedOutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateValidatorTest {

    private final LoadLockoutStateRequest request = DefaultLoadLockoutStateRequest.builder().build();
    private final LockoutStateLoader stateLoader = mock(LockoutStateLoader.class);

    private final LockoutStateValidator stateValidator = LockoutStateValidator.builder()
            .stateLoader(stateLoader)
            .build();

    @Test
    void shouldReturnTrueIfNotLocked() {
        final LockoutState notLockedState = mock(LockoutState.class);
        given(notLockedState.isLocked()).willReturn(false);
        given(stateLoader.load(request)).willReturn(notLockedState);

        final boolean result = stateValidator.validateState(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldThrowExceptionIfLocked() {
        final LockoutState lockedState = mock(LockoutState.class);
        given(lockedState.isLocked()).willReturn(true);
        given(stateLoader.load(request)).willReturn(lockedState);

        final LockedOutException error = (LockedOutException) catchThrowable(() -> stateValidator.validateState(request));

        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }

}
