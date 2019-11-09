package uk.co.mruoc.idv.api.verificationcontext.error;

import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.entities.lockout.LockoutStateMaxAttempts;

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
