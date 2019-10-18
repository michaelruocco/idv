package uk.co.mruoc.idv.verificationcontext.domain.model.method;

public class PushNotificationIneligible extends AbstractVerificationMethodIneligible implements PushNotification {

    public PushNotificationIneligible() {
        super(NAME, new NoMobileApplication());
    }

}
