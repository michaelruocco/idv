package uk.co.idv.api.lockout.state;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
