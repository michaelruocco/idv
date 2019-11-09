package uk.co.mruoc.idv.api.lockout;

import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class LockoutStateDocument extends ApiDocumentWithId<LockoutState> {

    public LockoutStateDocument(final LockoutState attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<LockoutState> {

        public Data(final LockoutState attributes) {
            super(attributes.getId(), "lockoutStates", attributes);
        }

    }

}
