package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class VerificationContextExpiredErrorItem extends JsonApiErrorItem {

    private static final String TITLE = "Verification Context Expired";
    private static final int STATUS = 410;

    public VerificationContextExpiredErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private VerificationContextExpiredErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private VerificationContextExpiredErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
