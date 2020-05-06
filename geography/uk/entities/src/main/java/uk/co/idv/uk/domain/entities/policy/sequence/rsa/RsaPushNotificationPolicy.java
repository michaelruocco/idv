package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy.PushNotificationPolicy;

import java.time.Duration;

public class RsaPushNotificationPolicy extends PushNotificationPolicy {

    public RsaPushNotificationPolicy() {
        super(buildParams());
    }

    private static VerificationMethodParams buildParams() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(5)
                .duration(Duration.ofMinutes(5))
                .build();
    }

}
