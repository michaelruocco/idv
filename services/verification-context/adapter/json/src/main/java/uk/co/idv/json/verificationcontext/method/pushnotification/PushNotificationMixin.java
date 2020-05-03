package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

import java.time.Duration;


@JsonPropertyOrder({
        "name",
        "eligible",
        "reason",
        "complete",
        "successful",
        "parameters"
})
public interface PushNotificationMixin extends VerificationMethodMixin {

    @JsonProperty("parameters")
    String getParams();

    //TODO move up to verification method mixin once all methods are updated to use params
    @JsonIgnore
    int getMaxAttempts();

    @JsonIgnore
    Duration getDuration();

}
