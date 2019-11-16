package uk.co.idv.repository.mongo.channel;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.repository.mongo.channel.simple.SingleSimpleChannelDocumentConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ChannelDocumentConverterTest {

    private final ChannelDocumentConverter converter = new SingleSimpleChannelDocumentConverter("fake-channel");
    private final ChannelDocumentConverterDelegator delegator = new ChannelDocumentConverterDelegator(converter);

    @Test
    void shouldConvertToSimpleChannel() {
        final ChannelDocument document = ChannelDocumentMother.fake();

        final Channel channel = delegator.toChannel(document);

        assertThat(channel).isInstanceOf(SimpleChannel.class);
    }

    @Test
    void shouldPopulateChannelId() {
        final ChannelDocument document = ChannelDocumentMother.fake();

        final Channel channel = delegator.toChannel(document);

        assertThat(channel.getId()).isEqualTo(document.getId());
    }

    @Test
    void shouldThrowExceptionForNotSupportedChannel() {
        final ChannelDocument document = ChannelDocumentMother.notSupported();

        final Throwable error = catchThrowable(() -> delegator.toChannel(document));

        assertThat(error).isInstanceOf(ChannelNotSupportedException.class);
    }

    @Test
    void shouldThrowExceptionWithChannelIdForNotSupportedChannel() {
        final ChannelDocument document = ChannelDocumentMother.notSupported();

        final Throwable error = catchThrowable(() -> delegator.toChannel(document));

        assertThat(error.getMessage()).isEqualTo(document.getId());
    }

    @Test
    void shouldConvertToDocument() {
        final Channel channel = ChannelMother.fake();

        final ChannelDocument document = delegator.toDocument(channel);

        assertThat(document.getId()).isEqualTo(channel.getId());
    }

}
