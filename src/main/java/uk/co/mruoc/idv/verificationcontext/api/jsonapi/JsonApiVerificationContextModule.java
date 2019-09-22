package uk.co.mruoc.idv.verificationcontext.api.jsonapi;

import uk.co.mruoc.idv.verificationcontext.api.VerificationContextModule;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

public class JsonApiVerificationContextModule extends VerificationContextModule {

    public JsonApiVerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, JsonApiVerificationContextMixin.class);

        addDeserializer(CreateContextRequestDocument.class, new CreateContextRequestDocumentDeserializer());
    }

}
