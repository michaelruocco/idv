package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;

import java.util.Collection;

public class OneTimePasscodeSmsEligible extends OneTimePasscodeSms {

    @Builder
    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<MobileNumber> mobileNumbers) {
        super(new Eligible(), passcodeSettings, mobileNumbers);
    }

}
