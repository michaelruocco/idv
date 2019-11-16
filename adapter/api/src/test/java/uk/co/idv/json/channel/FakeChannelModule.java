package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.json.channel.simple.SingleSimpleChannelJsonNodeConverter;

public class FakeChannelModule extends SimpleModule {

    public FakeChannelModule() {
        addDeserializer(Channel.class, buildDeserializer());
    }

    private static ChannelDeserializer buildDeserializer() {
        return new ChannelDeserializer(new SingleSimpleChannelJsonNodeConverter("fake-channel"));
    }

}
