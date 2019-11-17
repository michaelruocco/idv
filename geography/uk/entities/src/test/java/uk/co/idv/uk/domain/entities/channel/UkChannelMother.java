package uk.co.idv.uk.domain.entities.channel;

import uk.co.idv.uk.domain.entities.channel.Rsa.RsaBuilder;

public class UkChannelMother {

    public static Rsa rsa() {
        return new Rsa();
    }

    public static RsaBuilder rsaBuilder() {
        return Rsa.builder();
    }

}
