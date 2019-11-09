package uk.co.mruoc.idv.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String channelId = "activity-name";

        final Throwable error = new ChannelNotSupportedException(channelId);

        assertThat(error.getMessage()).isEqualTo(channelId);
    }

}
