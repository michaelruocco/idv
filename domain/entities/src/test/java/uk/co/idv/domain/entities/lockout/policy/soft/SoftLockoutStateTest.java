package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockoutStateTest {

    private final VerificationAttempts attempts = new FakeVerificationAttempts();

    private final SoftLockoutState.SoftLockoutStateBuilder builder = SoftLockoutState.builder()
        .attempts(attempts);

    @Test
    void shouldReturnIdFromAttempts() {
        final LockoutState state = builder.build();

        assertThat(state.getId()).isEqualTo(attempts.getId());
    }

    @Test
    void shouldReturnIdvIdFromAttempts() {

        final LockoutState state = builder.build();

        assertThat(state.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldReturnAttempts() {

        final LockoutState state = builder.build();

        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldBeLocked() {
        final LockoutState state = builder.build();

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnMessage() {
        final Instant lockedUntil = Instant.parse("2019-11-18T20:54:38.913Z");

        final LockoutState state = builder
                .lockedUntil(lockedUntil)
                .build();

        assertThat(state.getMessage()).isEqualTo(String.format("locked until %s", lockedUntil));
    }

    @Test
    void shouldReturnLockedUntil() {
        final Instant lockedUntil = Instant.parse("2019-11-18T20:54:38.913Z");

        final SoftLockoutState state = builder
                .lockedUntil(lockedUntil)
                .build();

        assertThat(state.getLockedUntil()).isEqualTo(lockedUntil);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(5);

        final SoftLockoutState state = builder
                .duration(duration)
                .build();

        assertThat(state.getDuration()).isEqualTo(duration);
    }

}
