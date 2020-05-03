package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams.DefaultVerificationMethodParamsBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification.PushNotificationBuilder;

import java.time.Duration;

public class PushNotificationMother {

    public static PushNotification ineligible() {
        return PushNotification.ineligible(new NoMobileApplication());
    }

    public static PushNotification eligible() {
        return eligibleBuilder().build();
    }

    public static PushNotificationBuilder eligibleBuilder() {
        return PushNotification.eligibleBuilder()
                .params(paramsBuilder().build());
    }

    public static VerificationMethodParams paramsWithMaxAttempts(int maxAttempts) {
        return paramsBuilder()
                .maxAttempts(maxAttempts)
                .build();
    }

    public static DefaultVerificationMethodParamsBuilder paramsBuilder() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(5)
                .duration(Duration.ofMinutes(5));
    }

}
