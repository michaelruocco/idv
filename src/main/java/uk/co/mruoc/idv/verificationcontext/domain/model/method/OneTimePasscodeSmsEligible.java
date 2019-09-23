package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;

import java.time.Duration;
import java.util.Collection;

public class OneTimePasscodeSmsEligible extends OneTimePasscodeSms {

    @Builder
    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<MobileNumber> mobileNumbers) {
        super(new Eligible(), Duration.ofMinutes(5), passcodeSettings, mobileNumbers);
    }

}
