package uk.co.idv.json.channel.simple;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.json.channel.ChannelDeserializer;

public class SimpleChannelModule extends SimpleModule {

    public SimpleChannelModule() {
        addDeserializer(Channel.class, new ChannelDeserializer(new AllSimpleChannelJsonNodeConverter()));
    }

}
