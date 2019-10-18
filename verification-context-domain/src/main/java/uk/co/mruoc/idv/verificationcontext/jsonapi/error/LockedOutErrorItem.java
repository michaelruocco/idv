package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import java.util.Map;
import java.util.UUID;

import static uk.co.mruoc.idv.verificationcontext.jsonapi.error.LockoutStateToMetaConverter.toMeta;

public class LockedOutErrorItem extends JsonApiErrorItem {

    private static final String TITLE = "Locked";
    private static final int STATUS = 423;

    public LockedOutErrorItem(final LockoutState lockoutState) {
        this(UUID.randomUUID(), lockoutState.getMessage(), toMeta(lockoutState));
    }

    private LockedOutErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
