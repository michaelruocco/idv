package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

import java.time.Duration;


@JsonPropertyOrder({
        "name",
        "function",
        "eligible",
        "reason",
        "complete",
        "successful",
        "parameters"
})
public interface PinsentryVerificationMethodMixin extends VerificationMethodMixin {

    @JsonProperty("parameters")
    PinsentryParams getParams();

    @JsonIgnore
    PinsentryFunction getFunction();

    //TODO move up to verification method mixin once all methods are updated to use params
    @JsonIgnore
    int getMaxAttempts();

    @JsonIgnore
    Duration getDuration();

}
