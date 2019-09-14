package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.util.Collections;

public class OneTimePasscodeSmsIneligible extends OneTimePasscodeSms {

    public OneTimePasscodeSmsIneligible(final PasscodeSettings passcodeSettings) {
        super(new NoEligibleMobileNumbers(), passcodeSettings, Collections.emptyList());
    }

}
