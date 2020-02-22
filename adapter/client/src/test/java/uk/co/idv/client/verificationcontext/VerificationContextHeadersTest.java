package uk.co.idv.client.verificationcontext;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextHeadersTest {

    @Test
    void shouldSetCorrelationId() {
        final String correlationId = UUID.randomUUID().toString();

        final IdvHeaders headers = VerificationContextHeaders.builder()
                .correlationId(correlationId)
                .build();

        assertThat(headers.getCorrelationId()).isEqualTo(correlationId);
    }

    @Test
    void shouldSetChannelId() {
        final String channelId = "channelId";

        final IdvHeaders headers = VerificationContextHeaders.builder()
                .channelId(channelId)
                .build();

        assertThat(headers.getChannelId()).isEqualTo(channelId);
    }

}
