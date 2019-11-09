package uk.co.idv.repository.mongo.channel;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.exception.ChannelNotSupportedException;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.Rsa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ChannelDocumentConverterTest {

    private final ChannelDocumentConverter converter = new ChannelDocumentConverter();

    @Test
    void shouldConvertToChannel() {
        final ChannelDocument document = ChannelDocumentMother.rsa();

        final Channel channel = converter.toChannel(document);

        assertThat(channel).isInstanceOf(Rsa.class);
    }

    @Test
    void shouldThrowExceptionForNotSupportedChannel() {
        final ChannelDocument document = ChannelDocumentMother.notSupported();

        final Throwable error = catchThrowable(() -> converter.toChannel(document));

        assertThat(error).isInstanceOf(ChannelNotSupportedException.class);
    }

    @Test
    void shouldThrowExceptionWithChannelIdForNotSupportedChannel() {
        final ChannelDocument document = ChannelDocumentMother.notSupported();

        final Throwable error = catchThrowable(() -> converter.toChannel(document));

        assertThat(error.getMessage()).isEqualTo(document.getId());
    }

    @Test
    void shouldConvertToDocument() {
        final Channel channel = new Rsa();

        final ChannelDocument document = converter.toDocument(channel);

        assertThat(document.getId()).isEqualTo(channel.getId());
    }

}
