package uk.co.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.lockout.LockoutPolicyParametersMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "level",
        "recordAttempts",
        "type",
        "maxNumberOfAttempts"
})
public interface JsonApiLockoutPolicyParametersMixin extends LockoutPolicyParametersMixin {

    @JsonIgnore
    UUID getId();

}
