package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.json.verificationcontext.method.DefaultVerificationMethodDeserializer;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.OneTimePasscodeMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.mobile.MobilePinsentryMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMethodModule;
import uk.co.idv.json.verificationcontext.method.pushnotification.PushNotificationMethodModule;
import uk.co.idv.json.verificationcontext.result.VerificationResultsModule;

import java.util.Arrays;

public class VerificationContextModule extends SimpleModule {

    public VerificationContextModule() {
        super("verification-context-module", Version.unknownVersion());

        setMixInAnnotation(VerificationContext.class, VerificationContextMixin.class);

        addSerializer(VerificationSequences.class, new VerificationSequencesSerializer());
        addSerializer(VerificationSequence.class, new VerificationSequenceSerializer());

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
        addDeserializer(VerificationContext.class, new VerificationContextDeserializer());
        addDeserializer(VerificationSequences.class, new VerificationSequencesDeserializer());
        addDeserializer(VerificationSequence.class, new VerificationSequenceDeserializer());
        addDeserializer(VerificationMethod.class, new DefaultVerificationMethodDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ChannelModule(),
                new ActivityModule(),
                new IdentityModule(),
                new VerificationResultsModule(),
                new OneTimePasscodeMethodModule(),
                new MobilePinsentryMethodModule(),
                new PhysicalPinsentryMethodModule(),
                new PushNotificationMethodModule()
        );
    }

}
