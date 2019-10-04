package uk.co.mruoc.idv.lockout.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.lockout.api.LockoutStateMaxAttemptsMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "idvId",
        "locked",
        "type",
        "maxNumberOfAttempts",
        "numberOfAttemptsRemaining",
        "created",
        "expiry"
})
public interface JsonApiLockoutStateMaxAttemptsMixin extends LockoutStateMaxAttemptsMixin {

    @JsonIgnore
    UUID getId();

}
