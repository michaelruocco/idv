package uk.co.idv.api.verificationcontext.error;

import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutState;

import java.util.Collections;
import java.util.Map;

public class LockoutStateToMetaConverter {

    public static Map<String, Object> toMeta(final LockoutState lockoutState) {
        if (lockoutState instanceof HardLockoutState) {
            return toMeta((HardLockoutState) lockoutState);
        }
        return Collections.emptyMap();
    }

    private static Map<String, Object> toMeta(final HardLockoutState lockoutState) {
        return Map.of(
                "maxNumberOfAttempts", lockoutState.getMaxNumberOfAttempts(),
                "numberOfAttemptsRemaining", lockoutState.getNumberOfAttemptsRemaining(),
                "idvId", lockoutState.getIdvId()
        );
    }

}
