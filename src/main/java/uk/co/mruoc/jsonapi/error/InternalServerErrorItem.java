package uk.co.mruoc.jsonapi.error;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class InternalServerErrorItem extends JsonApiErrorItem {

    private static final String TITLE = "Internal Server Error";
    private static final int STATUS = 500;

    public InternalServerErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    public InternalServerErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    public InternalServerErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, STATUS, TITLE, detail, meta);
    }

}
