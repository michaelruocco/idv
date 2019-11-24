package uk.co.idv.api.lockout.policy.hard;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.api.lockout.state.LockoutStateMixin;

@JsonPropertyOrder({
        "id",
        "idvId",
        "locked",
        "maxNumberOfAttempts",
        "numberOfAttemptsRemaining",
        "attempts"
})
public interface HardLockoutStateMixin extends LockoutStateMixin {

    // intentionally blank

}
