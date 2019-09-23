package uk.co.mruoc.idv.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiSingleErrorDocument;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ChannelNotSupportedErrorDocument extends JsonApiSingleErrorDocument {

    public ChannelNotSupportedErrorDocument(final String detail) {
        super(new ChannelNotSupportedErrorItem(detail));
    }

    private static class ChannelNotSupportedErrorItem extends JsonApiErrorItem {

        private static final String TITLE = "Channel Not Supported";
        private static final int STATUS = 400;

        private ChannelNotSupportedErrorItem(final String detail) {
            this(UUID.randomUUID(), detail);
        }

        private ChannelNotSupportedErrorItem(final UUID id, final String detail) {
            this(id, detail, Collections.emptyMap());
        }

        private ChannelNotSupportedErrorItem(final UUID id, final String detail, final Map<String, Object> meta) {
            super(id, STATUS, TITLE, detail, meta);
        }

    }

}
