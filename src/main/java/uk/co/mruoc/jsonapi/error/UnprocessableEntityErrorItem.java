package uk.co.mruoc.jsonapi.error;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class UnprocessableEntityErrorItem extends JsonApiErrorItem {

    private static final int STATUS = 422;

    public UnprocessableEntityErrorItem(final String title, final String detail) {
        this(UUID.randomUUID(), title, detail);
    }

    public UnprocessableEntityErrorItem(final UUID id, final String title, final String detail) {
        this(id, title, detail, Collections.emptyMap());
    }

    public UnprocessableEntityErrorItem(final UUID id, final String title, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, title, detail, meta);
    }

}
