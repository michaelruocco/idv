package uk.co.idv.uk.repository.mongo.channel;

import uk.co.idv.uk.domain.entities.channel.Rsa;

public class UkChannelDocumentMother {

    public static RsaChannelDocument rsa() {
        final RsaChannelDocument document = new RsaChannelDocument();
        document.setId(Rsa.ID);
        return document;
    }

    public static RsaChannelDocument rsaWithIssuerSessionId() {
        final RsaChannelDocument document = rsa();
        document.setIssuerSessionId("1234567890");
        return document;
    }

}
