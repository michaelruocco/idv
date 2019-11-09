package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.mruoc.idv.api.verificationcontext.UpdateContextResultsRequestDocumentDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultSerializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultsSerializer;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.entities.verificationcontext.method.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;

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
        addSerializer(VerificationResults.class, new VerificationResultsSerializer());
        addSerializer(VerificationResult.class, new VerificationResultSerializer());

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
        addDeserializer(VerificationResult.class, new VerificationResultDeserializer());
        addDeserializer(UpdateContextResultsRequestDocument.class, new UpdateContextResultsRequestDocumentDeserializer());
    }

}
