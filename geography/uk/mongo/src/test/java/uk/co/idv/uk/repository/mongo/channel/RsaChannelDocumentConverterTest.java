package uk.co.idv.uk.repository.mongo.channel;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.uk.domain.entities.channel.Rsa;
import uk.co.idv.uk.domain.entities.channel.UkChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class RsaChannelDocumentConverterTest {

    private final RsaChannelDocumentConverter converter = new RsaChannelDocumentConverter();

    @Test
    void shouldSupportRsaChannel() {
        assertThat(converter.supportsChannel(Rsa.ID)).isTrue();
    }

    @Test
    void shouldNotSupportOtherChannels() {
        assertThat(converter.supportsChannel("other-channel")).isFalse();
    }

    @Test
    void shouldConvertToDocument() {
        final Channel channel = UkChannelMother.rsa();

        final ChannelDocument document = converter.toDocument(channel);

        assertThat(document).isInstanceOf(RsaChannelDocument.class);
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final Channel channel = UkChannelMother.rsa();

        final ChannelDocument document = converter.toDocument(channel);

        assertThat(document.getId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldPopulateIssuerSessionIdAsNullOnDocumentIfNotProvided() {
        final Channel channel = UkChannelMother.rsa();

        final RsaChannelDocument document = (RsaChannelDocument) converter.toDocument(channel);

        assertThat(document.getIssuerSessionId()).isNull();
    }

    @Test
    void shouldPopulateIssuerSessionIdOnDocumentIfProvided() {
        final String issuerSessionId = "1234567890";
        final Rsa channel = UkChannelMother.rsaBuilder()
                .issuerSessionId(issuerSessionId)
                .build();

        final RsaChannelDocument document = (RsaChannelDocument) converter.toDocument(channel);

        assertThat(document.getIssuerSessionId()).isEqualTo(issuerSessionId);
    }

    @Test
    void shouldConvertToChannel() {
        final ChannelDocument document = UkChannelDocumentMother.rsa();

        final Channel channel = converter.toChannel(document);

        assertThat(channel).isInstanceOf(Rsa.class);
    }

    @Test
    void shouldPopulateChannelWithEmptyIssuerSessionIdIfNotProvided() {
        final ChannelDocument document = UkChannelDocumentMother.rsa();

        final Rsa channel = (Rsa) converter.toChannel(document);

        assertThat(channel.getIssuerSessionId()).isEmpty();
    }

    @Test
    void shouldPopulateChannelWithIssuerSessionIdIfProvided() {
        final RsaChannelDocument document = UkChannelDocumentMother.rsaWithIssuerSessionId();

        final Rsa channel = (Rsa) converter.toChannel(document);

        assertThat(channel.getIssuerSessionId()).contains(document.getIssuerSessionId());
    }

}
