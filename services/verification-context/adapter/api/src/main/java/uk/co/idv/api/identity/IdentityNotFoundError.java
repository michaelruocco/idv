package uk.co.idv.api.identity;

import uk.co.mruoc.jsonapi.error.ApiError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class IdentityNotFoundError extends ApiError {

    private static final String TITLE = "Identity Not Found";
    private static final int STATUS = 404;

    public IdentityNotFoundError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private IdentityNotFoundError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private IdentityNotFoundError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
