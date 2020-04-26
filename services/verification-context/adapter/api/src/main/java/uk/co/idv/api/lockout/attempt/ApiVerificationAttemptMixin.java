package uk.co.idv.api.lockout.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({
        "contextId",
        "channelId",
        "activityName",
        "methodName",
        "alias",
        "verificationId",
        "timestamp",
        "successful"
})
public interface ApiVerificationAttemptMixin {

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    String getAliasType();

    @JsonIgnore
    boolean isCardNumber();

}
