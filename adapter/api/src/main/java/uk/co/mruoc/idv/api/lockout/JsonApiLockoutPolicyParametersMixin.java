package uk.co.mruoc.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.json.lockout.LockoutPolicyParametersMixin;

import java.util.UUID;

@JsonPropertyOrder({
        "channelId",
        "activityName",
        "aliasType",
        "type",
        "recordAttemptStrategy",
        "maxNumberOfAttempts"
})
public interface JsonApiLockoutPolicyParametersMixin extends LockoutPolicyParametersMixin {

    @JsonIgnore
    UUID getId();

}
