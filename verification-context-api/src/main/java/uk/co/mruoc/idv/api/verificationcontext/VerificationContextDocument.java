package uk.co.mruoc.idv.api.verificationcontext;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
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
