package uk.co.idv.domain.usecases.verificationcontext.sequences;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;

import java.time.Duration;

@RequiredArgsConstructor
public class PushNotificationParameters {

    private final int maxAttempts;
    private final Duration duration;

    public VerificationMethod buildEligibleMethod() {
        return new PushNotificationEligible(maxAttempts, duration);
    }

}
