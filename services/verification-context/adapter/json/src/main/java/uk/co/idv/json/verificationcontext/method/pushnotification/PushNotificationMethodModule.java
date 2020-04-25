package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;
import uk.co.idv.json.verificationcontext.result.VerificationResultsModule;

import java.util.Arrays;

public class PushNotificationMethodModule extends SimpleModule {

    public PushNotificationMethodModule() {
        super("push-notification-method-module", Version.unknownVersion());

        addDeserializer(PushNotification.class, new PushNotificationDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new VerificationMethodModule(),
                new VerificationResultsModule()
        );
    }

}
