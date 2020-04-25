package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.OneTimePasscodeMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.mobile.MobilePinsentryMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMethodModule;
import uk.co.idv.json.verificationcontext.method.pushnotification.PushNotificationMethodModule;
import uk.co.idv.json.verificationcontext.sequence.VerificationSequencesModule;

import java.util.Arrays;

public class VerificationContextModule extends SimpleModule {

    public VerificationContextModule() {
        super("verification-context-module", Version.unknownVersion());

        setMixInAnnotation(VerificationContext.class, VerificationContextMixin.class);

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
        addDeserializer(VerificationContext.class, new VerificationContextDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ChannelModule(),
                new ActivityModule(),
                new IdentityModule(),
                new OneTimePasscodeMethodModule(),
                new MobilePinsentryMethodModule(),
                new PhysicalPinsentryMethodModule(),
                new PushNotificationMethodModule(),
                new VerificationSequencesModule()
        );
    }

}
