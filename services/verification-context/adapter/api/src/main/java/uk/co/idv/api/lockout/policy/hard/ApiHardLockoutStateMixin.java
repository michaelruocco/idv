package uk.co.idv.api.lockout.policy.hard;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.api.lockout.state.ApiLockoutStateMixin;

@JsonPropertyOrder({
        "id",
        "idvId",
        "locked",
        "maxNumberOfAttempts",
        "numberOfAttemptsRemaining",
        "attempts"
})
public interface ApiHardLockoutStateMixin extends ApiLockoutStateMixin {

    // intentionally blank

}
