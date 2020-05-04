package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

public interface PinsentryVerificationMethodMixin extends VerificationMethodMixin {

    @JsonIgnore
    PinsentryFunction getFunction();

}
