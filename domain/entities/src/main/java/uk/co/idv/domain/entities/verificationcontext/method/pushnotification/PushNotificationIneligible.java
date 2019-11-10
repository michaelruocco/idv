package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;

public class PushNotificationIneligible extends AbstractVerificationMethodIneligible implements PushNotification {

    public PushNotificationIneligible() {
        super(NAME, new NoMobileApplication());
    }

}
