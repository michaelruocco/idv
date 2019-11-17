package uk.co.idv.repository.mongo.channel.simple;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverter;
import uk.co.idv.repository.mongo.channel.ChannelDocumentMother;

import static org.assertj.core.api.Assertions.assertThat;

class SingleSimpleChannelDocumentConverterTest {

    private static final String SUPPORTED_CHANNEL_ID = "supported-channel-id";

    private final ChannelDocumentConverter converter = new SingleSimpleChannelDocumentConverter(SUPPORTED_CHANNEL_ID);

    @Test
    void shouldSupportSpecifiedChannelId() {
        assertThat(converter.supportsChannel(SUPPORTED_CHANNEL_ID)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherChannelIds() {
        assertThat(converter.supportsChannel("other-channel-id")).isFalse();
    }

    @Test
    void shouldConvertChannelToChannelDocument() {
        final Channel channel = ChannelMother.fake();

        final ChannelDocument document = converter.toDocument(channel);

        assertThat(document.getId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldConvertAnyChannelDocumentToSimpleChannel() {
        final ChannelDocument document = ChannelDocumentMother.fake();

        final Channel channel = converter.toChannel(document);

        assertThat(channel).isInstanceOf(SimpleChannel.class);
    }

    @Test
    void shouldPopulateIdOnChannel() {
        final ChannelDocument document = ChannelDocumentMother.fake();

        final Channel channel = converter.toChannel(document);

        assertThat(channel.getId()).isEqualTo(document.getId());
    }

}