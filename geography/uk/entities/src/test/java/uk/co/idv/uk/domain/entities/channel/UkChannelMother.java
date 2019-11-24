package uk.co.idv.uk.domain.entities.channel;

import uk.co.idv.uk.domain.entities.channel.as3.As3;
import uk.co.idv.uk.domain.entities.channel.rsa.Rsa;
import uk.co.idv.uk.domain.entities.channel.rsa.Rsa.RsaBuilder;

public class UkChannelMother {

    public static As3 as3() {
        return new As3();
    }

    public static Rsa rsa() {
        return new Rsa();
    }

    public static RsaBuilder rsaBuilder() {
        return Rsa.builder();
    }

}
