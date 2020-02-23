package uk.co.idv.client;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultIdvHeadersTest {

    @Test
    void shouldSetCorrelationId() {
        final String correlationId = UUID.randomUUID().toString();

        final IdvHeaders headers = DefaultIdvHeaders.builder()
                .correlationId(correlationId)
                .build();

        assertThat(headers.getCorrelationId()).isEqualTo(correlationId);
    }

    @Test
    void shouldSetChannelId() {
        final String channelId = "channelId";

        final IdvHeaders headers = DefaultIdvHeaders.builder()
                .channelId(channelId)
                .build();

        assertThat(headers.getChannelId()).isEqualTo(channelId);
    }

}
