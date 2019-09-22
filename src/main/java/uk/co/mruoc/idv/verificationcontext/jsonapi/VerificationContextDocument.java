package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.jsonapi.JsonApiDocumentWithId;

public class VerificationContextDocument extends JsonApiDocumentWithId<VerificationContext> {

    public VerificationContextDocument(final VerificationContext attributes) {
        super(attributes.getId(), "verificationContexts", attributes);
    }

}
