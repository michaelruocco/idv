package uk.co.mruoc.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.json.lockout.LockoutPolicyParametersMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "type",
        "maxNumberOfAttempts",
        "recordAttemptStrategy",
        "channelIds",
        "activityNames",
        "aliasTypes"
})
public interface JsonApiLockoutPolicyParametersMixin extends LockoutPolicyParametersMixin {

    @JsonIgnore
    UUID getId();

}
