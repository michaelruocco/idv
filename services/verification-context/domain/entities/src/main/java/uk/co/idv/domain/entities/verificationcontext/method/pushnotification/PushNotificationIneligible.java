package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;
import java.util.Optional;

public class PushNotificationIneligible implements VerificationMethod, PushNotification {

    private static final Eligibility ELIGIBILITY = new NoMobileApplication();
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
        return ELIGIBILITY.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return ELIGIBILITY.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return ELIGIBILITY;
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
