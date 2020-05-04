package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;



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
    VerificationMethodParams getParams();

}
