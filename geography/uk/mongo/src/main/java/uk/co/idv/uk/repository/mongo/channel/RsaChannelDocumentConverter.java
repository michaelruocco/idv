package uk.co.idv.uk.repository.mongo.channel;

import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverter;
import uk.co.idv.uk.domain.entities.channel.Rsa;


public class RsaChannelDocumentConverter implements ChannelDocumentConverter {

    @Override
    public boolean supportsChannel(final String id) {
        return Rsa.ID.equals(id);
    }

    @Override
    public ChannelDocument toDocument(final Channel channel) {
        final Rsa rsa = (Rsa) channel;
        final RsaChannelDocument document = new RsaChannelDocument();
        document.setId(rsa.getId());
        rsa.getIssuerSessionId().ifPresent(id -> document.setIssuerSessionId(id.toString()));
        return document;
    }

    @Override
    public Channel toChannel(final ChannelDocument document) {
        final RsaChannelDocument rsaDocument = (RsaChannelDocument) document;
        return new Rsa(rsaDocument.getIssuerSessionId());
    }

}
