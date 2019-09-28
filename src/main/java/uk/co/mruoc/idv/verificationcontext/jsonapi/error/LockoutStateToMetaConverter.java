package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateMaxAttempts;

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
                "lockoutType", lockoutState.getType(),
                "idvId", lockoutState.getIdvId()
        );
    }

}
