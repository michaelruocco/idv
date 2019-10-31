package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.NotFoundError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class VerificationContextNotFoundError extends NotFoundError {

    private static final String TITLE = "Verification Context Not Found";

    public VerificationContextNotFoundError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private VerificationContextNotFoundError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private VerificationContextNotFoundError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
