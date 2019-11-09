package uk.co.mruoc.idv.json.channel;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.channel.Channel;

public class ChannelModule extends SimpleModule {

    public ChannelModule() {
        addDeserializer(Channel.class, new ChannelDeserializer());
    }

}
