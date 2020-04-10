package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

@JsonPropertyOrder({
        "level",
        "recordAttempts",
        "type",
        "maxNumberOfAttempts"
})
public interface LockoutPolicyAttributesMixin {

    @JsonIgnore
    UUID getId();

    @JsonProperty("level")
    LockoutLevel getLockoutLevel();

    @JsonProperty("recordAttempts")
    String getRecordAttempts();

    @JsonProperty("type")
    String getLockoutType();

}
