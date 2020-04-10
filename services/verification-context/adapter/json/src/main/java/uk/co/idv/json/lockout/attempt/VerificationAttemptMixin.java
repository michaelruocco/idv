package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({
        "contextId",
        "channelId",
        "activityName",
        "methodName",
        "idvId",
        "alias",
        "verificationId",
        "timestamp",
        "successful"
})
public interface VerificationAttemptMixin {

    @JsonProperty("idvId")
    UUID getIdvIdValue();

    @JsonIgnore
    String getAliasType();

    @JsonIgnore
    boolean isCardNumber();

}
