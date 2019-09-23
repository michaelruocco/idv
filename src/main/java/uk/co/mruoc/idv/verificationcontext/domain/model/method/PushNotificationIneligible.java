package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class PushNotificationIneligible extends PushNotification {

    public PushNotificationIneligible() {
        super(new NoMobileApplication(), Duration.ZERO);
    }

}
