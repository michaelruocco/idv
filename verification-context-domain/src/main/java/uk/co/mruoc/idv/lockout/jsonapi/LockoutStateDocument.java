package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
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
