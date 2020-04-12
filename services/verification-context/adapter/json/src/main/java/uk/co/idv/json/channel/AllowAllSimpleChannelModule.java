package uk.co.idv.json.channel;

import uk.co.idv.json.channel.converter.AllSimpleChannelJsonNodeConverter;

public class AllowAllSimpleChannelModule extends ChannelModule {

    public AllowAllSimpleChannelModule() {
        super(new ChannelDeserializer(new AllSimpleChannelJsonNodeConverter()));
    }

}
