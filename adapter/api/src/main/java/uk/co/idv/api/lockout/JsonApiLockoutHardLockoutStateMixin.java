package uk.co.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.lockout.HardLockoutStateMixin;

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
public interface JsonApiLockoutHardLockoutStateMixin extends HardLockoutStateMixin {

    @JsonIgnore
    UUID getId();

}
