package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.json.verificationcontext.method.DefaultVerificationMethodParamsDeserializer;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;

import java.util.Collections;

public class PushNotificationMethodModule extends SimpleModule {

    public PushNotificationMethodModule() {
        super("push-notification-method-module", Version.unknownVersion());

        addDeserializer(PushNotification.class, new PushNotificationDeserializer());
        addDeserializer(DefaultVerificationMethodParams.class, new DefaultVerificationMethodParamsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new VerificationMethodModule());
    }

}
