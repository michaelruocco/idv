package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "contextId",
        "created",
        "expiry",
        "complete",
        "status",
        "maxDeliveryAttempts",
        "maxAttempts"
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface OneTimePasscodeVerificationMixin {

    @JsonIgnore
    int getDeliveryAttemptsRemaining();

    @JsonIgnore
    int getAttemptsRemaining();

    @JsonIgnore
    boolean isSuccessful();

}
