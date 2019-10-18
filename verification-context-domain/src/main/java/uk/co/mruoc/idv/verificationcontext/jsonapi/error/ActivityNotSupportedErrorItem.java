package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.BadRequestErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ActivityNotSupportedErrorItem extends BadRequestErrorItem {

    private static final String TITLE = "Activity Not Supported";

    public ActivityNotSupportedErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private ActivityNotSupportedErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private ActivityNotSupportedErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
