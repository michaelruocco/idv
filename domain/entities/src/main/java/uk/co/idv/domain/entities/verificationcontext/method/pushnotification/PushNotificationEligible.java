package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

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
