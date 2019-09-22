package uk.co.mruoc.idv.verificationcontext.api.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.jsonapi.JsonApiDocument;

public class CreateContextRequestDocument extends JsonApiDocument<CreateContextRequest> {

    public CreateContextRequestDocument(final CreateContextRequest attributes) {
        super("verificationContexts", attributes);
    }

}
