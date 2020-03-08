package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;

public class OneTimePasscodeVerificationModule extends SimpleModule {

    public OneTimePasscodeVerificationModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, OneTimePasscodeVerificationMixin.class);

        addDeserializer(CreateOneTimePasscodeVerificationRequest.class, new CreateOneTimePasscodeVerificationRequestDeserializer());
        addDeserializer(UpdateOneTimePasscodeVerificationRequest.class, new UpdateOneTimePasscodeVerificationRequestDeserializer());
    }

}
