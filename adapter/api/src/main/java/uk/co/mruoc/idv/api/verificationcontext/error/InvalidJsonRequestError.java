package uk.co.mruoc.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.BadRequestError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class InvalidJsonRequestError extends BadRequestError {

    private static final String TITLE = "Invalid JSON Request";

    public InvalidJsonRequestError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private InvalidJsonRequestError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private InvalidJsonRequestError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
