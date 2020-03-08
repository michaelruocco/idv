package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.json.verificationcontext.method.DefaultVerificationMethodDeserializer;
import uk.co.idv.json.verificationcontext.method.VerificationMethodSerializer;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.DeliveryMethodDeserializer;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.OneTimePasscodeSerializer;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.PasscodeSettingsDeserializer;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.PasscodeSettingsSerializer;
import uk.co.idv.json.verificationcontext.method.pinsentry.mobile.MobilePinsentrySerializer;
import uk.co.idv.json.verificationcontext.method.pinsentry.physical.PhysicalPinsentrySerializer;
import uk.co.idv.json.verificationcontext.method.pushnotification.PushNotificationSerializer;
import uk.co.idv.json.verificationcontext.result.VerificationResultDeserializer;
import uk.co.idv.json.verificationcontext.result.VerificationResultSerializer;
import uk.co.idv.json.verificationcontext.result.VerificationResultsDeserializer;
import uk.co.idv.json.verificationcontext.result.VerificationResultsSerializer;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
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
        addSerializer(OneTimePasscode.class, new OneTimePasscodeSerializer());
        addSerializer(PasscodeSettings.class, new PasscodeSettingsSerializer());
        addSerializer(VerificationResults.class, new VerificationResultsSerializer());
        addSerializer(VerificationResult.class, new VerificationResultSerializer());

        addDeserializer(CreateContextRequest.class, new CreateContextRequestDeserializer());
        addDeserializer(VerificationResult.class, new VerificationResultDeserializer());
        addDeserializer(VerificationContext.class, new VerificationContextDeserializer());
        addDeserializer(VerificationSequences.class, new VerificationSequencesDeserializer());
        addDeserializer(VerificationSequence.class, new VerificationSequenceDeserializer());
        addDeserializer(VerificationMethod.class, new DefaultVerificationMethodDeserializer());
        addDeserializer(VerificationResults.class, new VerificationResultsDeserializer());
        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
        addDeserializer(PasscodeSettings.class, new PasscodeSettingsDeserializer());
    }

}
