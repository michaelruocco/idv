package uk.co.mruoc.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.BadRequestError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ActivityNotSupportedError extends BadRequestError {

    private static final String TITLE = "Activity Not Supported";

    public ActivityNotSupportedError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private ActivityNotSupportedError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private ActivityNotSupportedError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
