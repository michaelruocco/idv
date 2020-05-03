package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams.DefaultVerificationMethodParamsBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible.PushNotificationEligibleBuilder;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.time.Duration;

public class PushNotificationMother {

    public static PushNotificationIneligible ineligible() {
        return new PushNotificationIneligible();
    }

    public static PushNotificationEligible eligible() {
        return eligibleBuilder().build();
    }

    public static PushNotificationEligibleBuilder eligibleBuilder() {
        return PushNotificationEligible.builder()
                .params(params())
                .results(VerificationResultsMother.empty());
    }

    public static VerificationMethodParams params() {
        return paramsBuilder().build();
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
