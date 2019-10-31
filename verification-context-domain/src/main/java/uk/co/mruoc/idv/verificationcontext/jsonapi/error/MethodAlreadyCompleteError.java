package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.ApiError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class MethodAlreadyCompleteError extends ApiError {

    private static final String TITLE = "Method Already Complete";
    private static final int STATUS = 422;

    public MethodAlreadyCompleteError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private MethodAlreadyCompleteError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private MethodAlreadyCompleteError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
