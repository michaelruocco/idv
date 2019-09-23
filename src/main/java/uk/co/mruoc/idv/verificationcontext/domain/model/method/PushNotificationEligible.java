package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class PushNotificationEligible extends PushNotification {

    public PushNotificationEligible() {
        super(new Eligible(), Duration.ofMinutes(5));
    }

}
