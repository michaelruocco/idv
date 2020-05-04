package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.json.verificationcontext.method.VerificationMethodMixin;

public interface OneTimePasscodeMixin extends VerificationMethodMixin {

    @JsonProperty("parameters")
    OneTimePasscodeParams getParams();

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    int getMaxDeliveries();

}
