package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PushNotificationIneligible implements VerificationMethod, PushNotification {

    private final Ineligible ineligible;
    private final VerificationResults results;
    private final VerificationMethodParams parameters;

    public PushNotificationIneligible() {
        this(new NoMobileApplication(), new VerificationResultsAlwaysEmpty(), buildParameters());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxAttempts() {
        return parameters.getMaxAttempts();
    }

    @Override
    public Duration getDuration() {
        return parameters.getDuration();
    }

    @Override
    public boolean isEligible() {
        return ineligible.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return ineligible.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return ineligible;
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
        return results;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        throw new CannotAddResultToIneligibleMethodException(NAME);
    }

    @Override
    public VerificationMethodParams getParameters() {
        return parameters;
    }

    private static VerificationMethodParams buildParameters() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(0)
                .duration(Duration.ZERO)
                .build();
    }

}
