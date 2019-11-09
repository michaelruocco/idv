package uk.co.idv.domain.entities.verificationcontext.method;

public class PushNotificationIneligible extends AbstractVerificationMethodIneligible implements PushNotification {

    public PushNotificationIneligible() {
        super(NAME, new NoMobileApplication());
    }

}
