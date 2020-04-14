package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@EqualsAndHashCode
public class SoftLockoutStateFactory {

    public LockoutState build(final Duration duration, final CalculateLockoutStateRequest request) {
        final Instant lockedUntil = request.calculateLockedUntil(duration);
        final boolean isLocked = request.wasIssuedBefore(lockedUntil);

        if (!isLocked) {
            return new NotLockedState(request.getAttempts());
        }

        return SoftLockoutState.builder()
                .attempts(request.getAttempts())
                .duration(duration)
                .lockedUntil(lockedUntil)
                .build();
    }

}
