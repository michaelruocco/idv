package uk.co.mruoc.verificationcontext.domain.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class OneTimePasscodeSms implements VerificationMethod {

    private static final String NAME = "one-time-passcode-sms";
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final Eligibility eligibility;
    private final PasscodeSettings passcodeSettings;
    private final Collection<MobileNumber> mobileNumbers;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Duration getDuration() {
        return DURATION;
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
