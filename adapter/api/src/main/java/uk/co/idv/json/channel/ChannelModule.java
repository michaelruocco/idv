package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.channel.Channel;

public class ChannelModule extends SimpleModule {

    public ChannelModule(final ChannelDeserializer deserializer) {
        addDeserializer(Channel.class, deserializer);
    }

}
