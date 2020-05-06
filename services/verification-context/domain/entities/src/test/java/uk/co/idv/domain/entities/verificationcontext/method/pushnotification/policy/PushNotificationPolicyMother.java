package uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy;

import uk.co.idv.domain.entities.verificationcontext.method.params.VerificationMethodParamsMother;

public class PushNotificationPolicyMother {

    public static PushNotificationPolicy build() {
        return new PushNotificationPolicy(VerificationMethodParamsMother.eligible());
    }

}
