
package uk.co.idv.api.verificationcontext;

import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

public class JsonApiVerificationContextModule extends VerificationContextModule {

    public JsonApiVerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, JsonApiVerificationContextMixin.class);

        addDeserializer(CreateContextRequestDocument.class, new CreateContextRequestDocumentDeserializer());
    }

}
