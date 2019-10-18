package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.jsonapi.JsonApiDocument;

public class ResetLockoutStateDocument extends JsonApiDocument<LockoutRequest> {

    public ResetLockoutStateDocument(final LockoutRequest attributes) {
        super("lockoutStates", attributes);
    }

}
