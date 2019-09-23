package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;

public class VerificationContextModule extends SimpleModule {

    public VerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, VerificationContextMixin.class);

        addSerializer(VerificationSequences.class, new VerificationSequencesSerializer());
        addSerializer(VerificationSequence.class, new VerificationSequenceSerializer());
        addSerializer(VerificationMethod.class, new VerificationMethodSerializer());
        addSerializer(PushNotification.class, new PushNotificationSerializer());
        addSerializer(PhysicalPinsentry.class, new PhysicalPinsentrySerializer());
        addSerializer(MobilePinsentry.class, new MobilePinsentrySerializer());
        addSerializer(OneTimePasscodeSms.class, new OneTimePasscodeSmsSerializer());
        addSerializer(PasscodeSettings.class, new PasscodeSettingsSerializer());

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
    }

}
