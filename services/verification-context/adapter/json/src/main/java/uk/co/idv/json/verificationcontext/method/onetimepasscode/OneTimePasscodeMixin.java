package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

public interface OneTimePasscodeMixin extends VerificationMethodMixin {

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    int getMaxDeliveryAttempts();

}
