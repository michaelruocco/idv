package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.mruoc.idv.api.verificationcontext.UpdateContextResultsRequestDocumentDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultSerializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationResultsSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;
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
        addSerializer(VerificationResults.class, new VerificationResultsSerializer());
        addSerializer(VerificationResult.class, new VerificationResultSerializer());

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
        addDeserializer(VerificationResult.class, new VerificationResultDeserializer());
        addDeserializer(UpdateContextResultsRequestDocument.class, new UpdateContextResultsRequestDocumentDeserializer());
    }

}