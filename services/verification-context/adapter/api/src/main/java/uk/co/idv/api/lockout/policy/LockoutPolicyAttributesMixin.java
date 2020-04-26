package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "level",
        "recordAttempts",
        "type",
        "maxNumberOfAttempts"
})
public interface LockoutPolicyAttributesMixin extends LockoutPolicyMixin {

    @JsonIgnore
    UUID getId();

}
