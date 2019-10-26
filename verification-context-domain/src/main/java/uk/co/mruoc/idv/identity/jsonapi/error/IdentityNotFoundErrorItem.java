package uk.co.mruoc.idv.identity.jsonapi.error;

import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class IdentityNotFoundErrorItem extends JsonApiErrorItem {

    private static final String TITLE = "Identity Not Found";
    private static final int STATUS = 404;

    public IdentityNotFoundErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private IdentityNotFoundErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private IdentityNotFoundErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
