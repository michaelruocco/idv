package uk.co.idv.api.verificationcontext.error;

import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.LockoutStateMaxAttempts;

import java.util.Collections;
import java.util.Map;

public class LockoutStateToMetaConverter {

    public static Map<String, Object> toMeta(final LockoutState lockoutState) {
        if (lockoutState instanceof LockoutStateMaxAttempts) {
            return toMeta((LockoutStateMaxAttempts) lockoutState);
        }
        return Collections.emptyMap();
    }

    private static Map<String, Object> toMeta(final LockoutStateMaxAttempts lockoutState) {
        return Map.of(
                "maxNumberOfAttempts", lockoutState.getMaxNumberOfAttempts(),
                "numberOfAttemptsRemaining", lockoutState.getNumberOfAttemptsRemaining(),
                "idvId", lockoutState.getIdvId()
        );
    }

}
