package uk.co.mruoc.idv.api.channel;

import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.Rsa;

public class JsonNodeToRsaConverter {

    public static Channel toRsa() {
        return new Rsa();
    }

}
