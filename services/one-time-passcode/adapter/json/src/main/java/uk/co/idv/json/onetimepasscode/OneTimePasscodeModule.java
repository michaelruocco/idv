package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequest;

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
