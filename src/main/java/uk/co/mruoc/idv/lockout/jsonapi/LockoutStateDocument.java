package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.jsonapi.JsonApiDocumentWithId;

public class LockoutStateDocument extends JsonApiDocumentWithId<LockoutState> {

    public LockoutStateDocument(final LockoutState attributes) {
        super(attributes.getId(), "lockoutStates", attributes);
    }

}
