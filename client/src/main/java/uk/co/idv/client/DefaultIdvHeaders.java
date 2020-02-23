package uk.co.idv.client;

import lombok.Builder;
import uk.co.mruoc.rest.client.header.ApplicationJsonHeaders;

public class DefaultIdvHeaders extends ApplicationJsonHeaders implements IdvHeaders {

    private static final String CHANNEL_ID_NAME = "channel-id";
    private static final String CORRELATION_ID_NAME = "correlation-id";

    @Builder
    public DefaultIdvHeaders(final String channelId, final String correlationId) {
        set(CHANNEL_ID_NAME, channelId);
        set(CORRELATION_ID_NAME, correlationId);
    }

    @Override
    public String getCorrelationId() {
        return get(CORRELATION_ID_NAME);
    }

    @Override
    public String getChannelId() {
        return get(CHANNEL_ID_NAME);
    }

}
