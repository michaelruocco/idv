package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class OneTimePasscodeSmsEligible extends AbstractVerificationMethodEligible implements OneTimePasscodeSms {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PasscodeSettings passcodeSettings;
    private final Collection<MobileNumber> mobileNumbers;

    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<MobileNumber> mobileNumbers) {
        this(passcodeSettings, mobileNumbers, new DefaultVerificationResults());
    }

    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<MobileNumber> mobileNumbers,
                                      final VerificationResult result) {
        this(passcodeSettings, mobileNumbers, new DefaultVerificationResults(result));
    }

    public OneTimePasscodeSmsEligible(final PasscodeSettings passcodeSettings,
                                      final Collection<MobileNumber> mobileNumbers,
                                      final VerificationResults results) {
        super(NAME, results, MAX_ATTEMPTS, DURATION);
        this.passcodeSettings = passcodeSettings;
        this.mobileNumbers = mobileNumbers;
    }

    public PasscodeSettings getPasscodeSettings() {
        return passcodeSettings;
    }

    public Collection<MobileNumber> getMobileNumbers() {
        return Collections.unmodifiableCollection(mobileNumbers);
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers, results);
    }

}
