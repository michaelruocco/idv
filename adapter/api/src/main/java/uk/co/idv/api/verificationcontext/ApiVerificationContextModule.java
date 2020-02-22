
package uk.co.idv.api.verificationcontext;

import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

public class ApiVerificationContextModule extends VerificationContextModule {

    public ApiVerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, ApiVerificationContextMixin.class);

        addDeserializer(CreateContextRequestDocument.class, new CreateContextRequestDocumentDeserializer());
        addDeserializer(UpdateContextResultsRequestDocument.class, new UpdateContextResultsRequestDocumentDeserializer());
        addDeserializer(VerificationContextDocument.class, new VerificationContextDocumentDeserializer());
    }

}
