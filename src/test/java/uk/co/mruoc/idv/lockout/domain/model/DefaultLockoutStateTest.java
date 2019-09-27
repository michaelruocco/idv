package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutState.DefaultLockoutStateBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DefaultLockoutStateTest {

    private final DefaultLockoutStateBuilder builder = DefaultLockoutState.builder();

    @Test
    void shouldReturnAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final LockoutState state = builder.attempts(attempts).build();

        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

}
