package uk.co.mruoc.verificationcontext.domain.method;

import lombok.Builder;

import java.util.Collection;

public class OneTimePasscodeSmsEligible extends OneTimePasscodeSms {

    @Builder
    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<String> mobileNumbers) {
        super(new Eligible(), passcodeSettings, mobileNumbers);
    }

}
