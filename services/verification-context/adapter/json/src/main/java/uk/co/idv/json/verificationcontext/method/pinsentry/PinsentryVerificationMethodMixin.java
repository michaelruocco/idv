package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;



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

}
