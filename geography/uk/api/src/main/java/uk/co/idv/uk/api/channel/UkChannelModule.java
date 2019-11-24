package uk.co.idv.uk.api.channel;

import uk.co.idv.json.channel.ChannelDeserializer;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.channel.simple.AllSimpleChannelJsonNodeConverter;
import uk.co.idv.uk.api.channel.rsa.RsaJsonNodeConverter;

public class UkChannelModule extends ChannelModule {

    public UkChannelModule() {
        super(new ChannelDeserializer(
                new RsaJsonNodeConverter(),
                new AllSimpleChannelJsonNodeConverter()
        ));
    }

}
