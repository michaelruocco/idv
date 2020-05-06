package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeParams;

public interface OneTimePasscodeParamsMixin {

    @JsonProperty("passcode")
    PasscodeParams getPasscodeParams();

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    int getPasscodeDuration();

    @JsonIgnore
    int getMaxDeliveries();

}
