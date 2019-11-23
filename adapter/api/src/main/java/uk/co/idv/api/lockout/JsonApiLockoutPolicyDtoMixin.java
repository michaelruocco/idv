package uk.co.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.lockout.LockoutPolicyDtoMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "level",
        "recordAttempts",
        "type",
        "maxNumberOfAttempts"
})
public interface JsonApiLockoutPolicyDtoMixin extends LockoutPolicyDtoMixin {

    @JsonIgnore
    UUID getId();

}
