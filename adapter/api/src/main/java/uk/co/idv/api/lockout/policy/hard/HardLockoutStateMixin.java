package uk.co.idv.api.lockout.policy.hard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

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
    UUID getId();

    @JsonIgnore
    String getMessage();

}
