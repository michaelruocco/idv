package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequest;

import java.util.Arrays;

public class OneTimePasscodeModule extends SimpleModule {

    public OneTimePasscodeModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, OneTimePasscodeVerificationMixin.class);
        setMixInAnnotation(OneTimePasscodeDelivery.class, OneTimePasscodeDeliveryMixin.class);

        addDeserializer(SendOneTimePasscodeRequest.class, new SendOneTimePasscodeRequestDeserializer());
        addDeserializer(ResendOneTimePasscodeRequest.class, new ResendOneTimePasscodeRequestDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequest.class, new VerifyOneTimePasscodeRequestDeserializer());

        addDeserializer(OneTimePasscodeDelivery.class, new OneTimePasscodeDeliveryDeserializer());
        addDeserializer(OneTimePasscodeVerificationAttempt.class, new OneTimePasscodeVerificationAttemptDeserializer());
        addDeserializer(OneTimePasscodeVerification.class, new OneTimePasscodeVerificationDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule()
        );
    }

}
