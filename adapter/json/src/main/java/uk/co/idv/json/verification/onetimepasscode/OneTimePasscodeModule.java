package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.VerifyOneTimePasscodeRequest;

public class OneTimePasscodeModule extends SimpleModule {

    public OneTimePasscodeModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, OneTimePasscodeVerificationMixin.class);

        addDeserializer(SendOneTimePasscodeRequest.class, new SendOneTimePasscodeRequestDeserializer());
        addDeserializer(ResendOneTimePasscodeRequest.class, new ResendOneTimePasscodeRequestDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequest.class, new VerifyOneTimePasscodeRequestDeserializer());

        addDeserializer(OneTimePasscodeDelivery.class, new OneTimePasscodeDeliveryDeserializer());
        addDeserializer(OneTimePasscodeVerificationAttempt.class, new OneTimePasscodeVerificationAttemptDeserializer());
        addDeserializer(OneTimePasscodeVerification.class, new OneTimePasscodeVerificationDeserializer());
    }

}
