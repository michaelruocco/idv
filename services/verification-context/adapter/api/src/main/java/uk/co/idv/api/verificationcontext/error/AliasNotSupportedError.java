package uk.co.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.BadRequestError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class AliasNotSupportedError extends BadRequestError {

    private static final String TITLE = "Alias Not Supported";

    public AliasNotSupportedError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private AliasNotSupportedError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private AliasNotSupportedError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
