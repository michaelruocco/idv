package uk.co.mruoc.idv.lockout.api;

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
public interface VerificationAttemptMixin {

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    String getAliasType();

}
