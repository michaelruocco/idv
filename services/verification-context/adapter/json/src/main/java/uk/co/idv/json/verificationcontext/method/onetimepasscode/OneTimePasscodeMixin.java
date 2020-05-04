package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

import java.time.Duration;

public interface OneTimePasscodeMixin extends VerificationMethodMixin {

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    int getMaxDeliveries();

    @JsonIgnore
    Duration getPasscodeDuration();

}
