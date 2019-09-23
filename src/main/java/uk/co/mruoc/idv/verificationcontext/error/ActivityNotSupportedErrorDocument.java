package uk.co.mruoc.idv.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiSingleErrorDocument;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ActivityNotSupportedErrorDocument extends JsonApiSingleErrorDocument {

    public ActivityNotSupportedErrorDocument(final String detail) {
        super(new ActivityNotSupportedErrorItem(detail));
    }

    private static class ActivityNotSupportedErrorItem extends JsonApiErrorItem {

        private static final String TITLE = "Activity Not Supported";
        private static final int STATUS = 400;

        private ActivityNotSupportedErrorItem(final String detail) {
            this(UUID.randomUUID(), detail);
        }

        private ActivityNotSupportedErrorItem(final UUID id, final String detail) {
            this(id, detail, Collections.emptyMap());
        }

        private ActivityNotSupportedErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
            super(id, STATUS, TITLE, detail, meta);
        }

    }

}
