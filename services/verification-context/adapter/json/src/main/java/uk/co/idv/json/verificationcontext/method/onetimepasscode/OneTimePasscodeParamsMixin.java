package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface OneTimePasscodeParamsMixin {

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    int getPasscodeDuration();

    @JsonIgnore
    int getMaxDeliveries();

}
