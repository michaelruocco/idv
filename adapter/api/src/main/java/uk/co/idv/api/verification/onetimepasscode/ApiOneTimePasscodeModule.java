
package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;

public class ApiOneTimePasscodeModule extends OneTimePasscodeModule {

    public ApiOneTimePasscodeModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, ApiOneTimePasscodeVerificationMixin.class);

        addDeserializer(SendOneTimePasscodeRequestDocument.class, new SendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(ResendOneTimePasscodeRequestDocument.class, new ResendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequestDocument.class, new VerifyOneTimePasscodeRequestDocumentDeserializer());
    }

}
