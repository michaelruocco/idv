package uk.co.mruoc.idv.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.BadRequestErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

class AliasNotSupportedErrorItem extends BadRequestErrorItem {

    private static final String TITLE = "Alias Not Supported";

    AliasNotSupportedErrorItem(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private AliasNotSupportedErrorItem(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private AliasNotSupportedErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
