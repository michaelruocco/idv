package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.params.VerificationMethodParamsMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible.PushNotificationEligibleBuilder;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

public class PushNotificationMother {

    public static PushNotification ineligible() {
        return new PushNotificationIneligible(new NoMobileApplication());
    }

    public static PushNotification eligible() {
        return new PushNotificationEligible(VerificationMethodParamsMother.eligible());
    }

    public static PushNotificationEligibleBuilder eligibleBuilder() {
        return PushNotificationEligible.builder()
                .params(VerificationMethodParamsMother.eligible())
                .results(new DefaultVerificationResults());
    }

}
