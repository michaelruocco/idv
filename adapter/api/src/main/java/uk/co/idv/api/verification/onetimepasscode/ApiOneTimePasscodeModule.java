
package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.json.verification.onetimepasscode.OneTimePasscodeModule;

public class ApiOneTimePasscodeModule extends OneTimePasscodeModule {

    public ApiOneTimePasscodeModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, ApiOneTimePasscodeVerificationMixin.class);

        addDeserializer(SendOneTimePasscodeRequestDocument.class, new SendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(ResendOneTimePasscodeRequestDocument.class, new ResendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequestDocument.class, new VerifyOneTimePasscodeRequestDocumentDeserializer());
    }

}
