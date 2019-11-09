package uk.co.mruoc.idv.api.lockout;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class ResetLockoutStateDocument extends ApiDocument<LockoutRequest> {

    public ResetLockoutStateDocument(final LockoutRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<LockoutRequest> {

        private Data(final LockoutRequest attributes) {
            super("lockoutStates", attributes);
        }

    }

}
