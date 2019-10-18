package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import uk.co.mruoc.jsonapi.error.UnprocessableEntityErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class NotNextMethodInSequenceErrorItem extends UnprocessableEntityErrorItem {

    private static final String TITLE = "Not Next Method in Sequence";

    public NotNextMethodInSequenceErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private NotNextMethodInSequenceErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private NotNextMethodInSequenceErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
