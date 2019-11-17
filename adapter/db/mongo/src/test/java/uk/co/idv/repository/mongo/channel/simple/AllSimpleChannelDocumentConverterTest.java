package uk.co.idv.repository.mongo.channel.simple;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverter;
import uk.co.idv.repository.mongo.channel.ChannelDocumentMother;

import static org.assertj.core.api.Assertions.assertThat;

class AllSimpleChannelDocumentConverterTest {

    private final ChannelDocumentConverter converter = new AllSimpleChannelDocumentConverter();

    @Test
    void shouldSupportAllChannelIds() {
        assertThat(converter.supportsChannel("ALL")).isTrue();
    }

    @Test
    void shouldConvertAnyChannelToChannelDocument() {
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
