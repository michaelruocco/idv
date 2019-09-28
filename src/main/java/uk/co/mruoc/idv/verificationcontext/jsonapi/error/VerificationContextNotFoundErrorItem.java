package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.NotFoundErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class VerificationContextNotFoundErrorItem extends NotFoundErrorItem {

    private static final String TITLE = "Verification Context Not Found";

    public VerificationContextNotFoundErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private VerificationContextNotFoundErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private VerificationContextNotFoundErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
