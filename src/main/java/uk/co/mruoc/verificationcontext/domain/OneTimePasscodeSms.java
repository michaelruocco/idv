package uk.co.mruoc.verificationcontext.domain;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class OneTimePasscodeSms implements VerificationMethod {

    private final Eligibility eligibility;
    private final PasscodeSettings passcodeSettings;
    private final Collection<String> mobileNumbers;

    @Override
    public String getName() {
        return Names.ONE_TIME_PASSCODE_SMS;
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMinutes(5);
    }

    @Override
    public boolean isEligible() {
        return eligibility.isEligible();
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

    public PasscodeSettings getPasscodeSettings() {
        return passcodeSettings;
    }

    public Collection<String> getMobileNumbers() {
        return Collections.unmodifiableCollection(mobileNumbers);
    }

}
