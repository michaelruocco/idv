package uk.co.idv.api.identity;

import uk.co.idv.domain.entities.identity.Identity;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class IdentityDocument extends ApiDocumentWithId<Identity> {

    public IdentityDocument(final Identity attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<Identity> {

        public Data(final Identity attributes) {
            super(attributes.getIdvIdValue(), "identities", attributes);
        }

    }

}
