package uk.co.mruoc.jsonapi.error;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class BadRequestErrorItem extends JsonApiErrorItem {

    private static final int STATUS = 400;

    public BadRequestErrorItem(final String title, final String detail) {
        this(UUID.randomUUID(), title, detail);
    }

    public BadRequestErrorItem(final UUID id, final String title, final String detail) {
        this(id, title, detail, Collections.emptyMap());
    }

    public BadRequestErrorItem(final UUID id, final String title, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, title, detail, meta);
    }

}
