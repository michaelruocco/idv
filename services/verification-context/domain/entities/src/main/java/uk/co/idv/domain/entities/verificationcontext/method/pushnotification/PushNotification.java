package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.params.IneligibleVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class PushNotification implements VerificationMethod {

    public static final String NAME = "push-notification";

    private final VerificationMethodParams params;
    private final Eligibility eligibility;
    private final VerificationResults results;

    public static PushNotificationBuilder eligibleBuilder() {
        return PushNotification.builder()
                .eligibility(new Eligible())
                .results(new DefaultVerificationResults());
    }

    public static PushNotification ineligible(final Ineligible reason) {
        return PushNotification.builder()
                .params(new IneligibleVerificationMethodParams())
                .eligibility(reason)
                .results(new DefaultVerificationResults())
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxAttempts() {
        return params.getMaxAttempts();
    }

    @Override
    public Duration getDuration() {
        return params.getDuration();
    }

    @Override
    public boolean isEligible() {
        return eligibility.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return eligibility.getReason();
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public boolean isComplete() {
        if (!isEligible()) {
            return false;
        }
        return VerificationMethodUtils.isComplete(results, params.getMaxAttempts());
    }

    @Override
    public boolean isSuccessful() {
        return results.containsSuccessful();
    }

    @Override
    public void addResult(final VerificationResult result) {
        if (!isEligible()) {
            throw new CannotAddResultToIneligibleMethodException(NAME);
        }
        VerificationMethodUtils.addResult(results, result, NAME, params.getMaxAttempts());
    }

}
