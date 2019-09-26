package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;

public class PushNotificationEligible extends AbstractVerificationMethodEligible implements PushNotification {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration DURATION = Duration.ofMinutes(5);

    public PushNotificationEligible() {
        this(new DefaultVerificationResults());
    }

    public PushNotificationEligible(final VerificationResult result) {
        this(new DefaultVerificationResults(result));
    }

    public PushNotificationEligible(final VerificationResults results) {
        super(NAME, results, MAX_ATTEMPTS, DURATION);
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new PushNotificationEligible(results);
    }

}
