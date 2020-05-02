package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;
import java.util.Optional;

public class OneTimePasscodeIneligible implements VerificationMethod, OneTimePasscode {

    private static final Eligibility INELIGIBLE = new NoEligibleDeliveryMethods();
    private static final VerificationResults RESULTS = new VerificationResultsAlwaysEmpty();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxAttempts() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    public boolean isEligible() {
        return INELIGIBLE.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return INELIGIBLE.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return INELIGIBLE;
    }

    @Override
    public boolean hasResults() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public VerificationResults getResults() {
        return RESULTS;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        throw new CannotAddResultToIneligibleMethodException(NAME);
    }

}
