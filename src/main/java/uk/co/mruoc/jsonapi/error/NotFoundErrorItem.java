package uk.co.mruoc.jsonapi.error;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class NotFoundErrorItem extends JsonApiErrorItem {

    private static final int STATUS = 404;

    public NotFoundErrorItem(final String title, final String detail) {
        this(UUID.randomUUID(), title, detail);
    }

    public NotFoundErrorItem(final UUID id, final String title, final String detail) {
        this(id, title, detail, Collections.emptyMap());
    }

    public NotFoundErrorItem(final UUID id, final String title, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, title, detail, meta);
    }

}
