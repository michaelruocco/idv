package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.jsonapi.JsonApiDocument;

public class ResetLockoutStateDocument extends JsonApiDocument<ResetLockoutStateAttributes> {

    public ResetLockoutStateDocument(final ResetLockoutStateAttributes attributes) {
        super("lockoutStates", attributes);
    }

}
