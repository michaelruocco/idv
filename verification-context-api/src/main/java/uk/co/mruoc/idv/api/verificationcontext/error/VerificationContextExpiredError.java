package uk.co.mruoc.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.ApiError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class VerificationContextExpiredError extends ApiError {

    private static final String TITLE = "Verification Context Expired";
    private static final int STATUS = 410;

    public VerificationContextExpiredError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private VerificationContextExpiredError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private VerificationContextExpiredError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
