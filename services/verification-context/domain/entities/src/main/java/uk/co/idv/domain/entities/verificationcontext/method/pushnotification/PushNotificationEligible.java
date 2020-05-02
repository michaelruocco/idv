package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class PushNotificationEligible implements VerificationMethod, PushNotification {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final VerificationResults results;
    private final int maxAttempts;
    private final Duration duration;
    private final Eligibility eligibility;

    public PushNotificationEligible() {
        this(new DefaultVerificationResults());
    }

    public PushNotificationEligible(final VerificationResult result) {
        this(new DefaultVerificationResults(result));
    }

    public PushNotificationEligible(final VerificationResults results) {
        this(MAX_ATTEMPTS, DURATION, results);
    }

    public PushNotificationEligible(final int maxAttempts, final Duration duration) {
        this(maxAttempts, duration, new DefaultVerificationResults());
    }

    public PushNotificationEligible(final int maxAttempts, final Duration duration, final VerificationResults results) {
        this(results, maxAttempts, duration, new Eligible());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public Duration getDuration() {
        return duration;
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
    public Eligibility getEligibility() {
        return eligibility;
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public boolean isComplete() {
        return VerificationMethodUtils.isComplete(results, maxAttempts);
    }

    @Override
    public boolean isSuccessful() {
        return results.containsSuccessful();
    }

    @Override
    public VerificationResults getResults() {
        return results;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, NAME, maxAttempts);
        return new PushNotificationEligible(updatedResults);
    }

}
