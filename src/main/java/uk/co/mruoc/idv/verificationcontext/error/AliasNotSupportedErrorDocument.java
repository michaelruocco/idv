package uk.co.mruoc.idv.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiSingleErrorDocument;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class AliasNotSupportedErrorDocument extends JsonApiSingleErrorDocument {

    public AliasNotSupportedErrorDocument(final String detail) {
        super(new AliasNotSupportedErrorItem(detail));
    }

    private static class AliasNotSupportedErrorItem extends JsonApiErrorItem {

        private static final String TITLE = "Alias Not Supported";
        private static final int STATUS = 400;

        private AliasNotSupportedErrorItem(final String detail) {
            this(UUID.randomUUID(), detail);
        }

        private AliasNotSupportedErrorItem(final UUID id, final String detail) {
            this(id, detail, Collections.emptyMap());
        }

        private AliasNotSupportedErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
            super(id, STATUS, TITLE, detail, meta);
        }

    }

}
