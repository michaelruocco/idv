
package uk.co.idv.api.onetimepasscode;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.mruoc.jsonapi.ApiModule;

public class ApiOneTimePasscodeModule extends ApiModule {

    public ApiOneTimePasscodeModule() {
        setMixInAnnotation(OneTimePasscodeVerification.class, ApiOneTimePasscodeVerificationMixin.class);

        addDeserializer(SendOneTimePasscodeRequestDocument.class, new SendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(ResendOneTimePasscodeRequestDocument.class, new ResendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequestDocument.class, new VerifyOneTimePasscodeRequestDocumentDeserializer());
    }

}
