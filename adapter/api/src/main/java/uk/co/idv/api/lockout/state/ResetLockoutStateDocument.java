package uk.co.idv.api.lockout.state;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class ResetLockoutStateDocument extends ApiDocument<LockoutRequest> {

    public ResetLockoutStateDocument(final LockoutRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<LockoutRequest> {

        private Data(final LockoutRequest attributes) {
            super("lockout-states", attributes);
        }

    }

}
