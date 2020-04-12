package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;

public class PushNotificationMethodModule extends SimpleModule {

    public PushNotificationMethodModule() {
        super("push-notification-method-module", Version.unknownVersion());

        addSerializer(PushNotification.class, new PushNotificationSerializer());
    }

}
