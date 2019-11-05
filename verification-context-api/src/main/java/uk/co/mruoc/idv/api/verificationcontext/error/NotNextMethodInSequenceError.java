package uk.co.mruoc.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.UnprocessableEntityError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class NotNextMethodInSequenceError extends UnprocessableEntityError {

    private static final String TITLE = "Not Next Method in Sequence";

    public NotNextMethodInSequenceError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private NotNextMethodInSequenceError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private NotNextMethodInSequenceError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
