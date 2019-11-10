package uk.co.idv.api.verificationcontext.error;

import uk.co.mruoc.jsonapi.error.BadRequestError;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ChannelNotSupportedError extends BadRequestError {

    private static final String TITLE = "Channel Not Supported";

    public ChannelNotSupportedError(final String detail) {
        this(UUID.randomUUID(), detail);
    }

    private ChannelNotSupportedError(final UUID id, final String detail) {
        this(id, detail, Collections.emptyMap());
    }

    private ChannelNotSupportedError(final UUID id, final String detail, final Map<String, Object> meta) {
        super(id, TITLE, detail, meta);
    }

}
