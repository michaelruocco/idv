package uk.co.idv.json.lockout;

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
public interface HardLockoutStateMixin {

    @JsonIgnore
    String getMessage();

}
