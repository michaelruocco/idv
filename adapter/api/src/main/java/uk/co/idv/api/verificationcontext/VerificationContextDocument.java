package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class VerificationContextDocument extends ApiDocumentWithId<VerificationContext> {

    public VerificationContextDocument(final VerificationContext attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<VerificationContext> {

        private Data(final VerificationContext attributes) {
            super(attributes.getId(), "verificationContexts", attributes);
        }

    }

}
