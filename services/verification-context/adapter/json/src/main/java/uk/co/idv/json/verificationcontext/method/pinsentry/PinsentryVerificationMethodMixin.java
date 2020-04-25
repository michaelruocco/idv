package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;


@JsonPropertyOrder({
        "name",
        "function",
        "eligible",
        "reason",
        "complete",
        "successful",
        "duration",
        "maxAttempts"
})
public interface PinsentryVerificationMethodMixin extends VerificationMethodMixin {

    // intentionally blank

}
