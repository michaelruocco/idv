package uk.co.mruoc.verificationcontext.domain;

import lombok.Builder;

import java.util.Collections;

public class OneTimePasscodeSmsIneligible extends OneTimePasscodeSms {

    @Builder
    public OneTimePasscodeSmsIneligible(final Ineligible ineligible, final PasscodeSettings passcodeSettings) {
        super(ineligible, passcodeSettings, Collections.emptyList());
    }

}
