package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class OneTimePasscodeSms implements VerificationMethod {

    private static final String NAME = "one-time-passcode-sms";

    private final Eligibility eligibility;
    private final Duration duration;
    private final PasscodeSettings passcodeSettings;
    private final Collection<MobileNumber> mobileNumbers;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

    public PasscodeSettings getPasscodeSettings() {
        return passcodeSettings;
    }

    public Collection<MobileNumber> getMobileNumbers() {
        return Collections.unmodifiableCollection(mobileNumbers);
    }

}
