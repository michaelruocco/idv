package uk.co.idv.api.verificationcontext;

import lombok.EqualsAndHashCode;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class VerificationContextDocument extends ApiDocumentWithId<VerificationContext> {

    public VerificationContextDocument(final VerificationContext attributes) {
        super(new Data(attributes));
    }

    @EqualsAndHashCode(callSuper = true)
    private static class Data extends ApiDataWithId<VerificationContext> {

        private Data(final VerificationContext attributes) {
            super(attributes.getId(), "verification-contexts", attributes);
        }

    }

}
