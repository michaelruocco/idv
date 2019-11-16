package uk.co.idv.uk.api.channel;

import uk.co.idv.json.channel.ChannelDeserializer;
import uk.co.idv.json.channel.ChannelModule;

public class UkChannelModule extends ChannelModule {

    public UkChannelModule() {
        super(new ChannelDeserializer(new RsaJsonNodeConverter()));
    }

}