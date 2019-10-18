package uk.co.mruoc.idv.lockout.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "idvId",
        "locked",
        "maxNumberOfAttempts",
        "numberOfAttemptsRemaining",
        "created",
        "expiry"
})
public interface LockoutStateMaxAttemptsMixin {

    @JsonIgnore
    String getMessage();

}
