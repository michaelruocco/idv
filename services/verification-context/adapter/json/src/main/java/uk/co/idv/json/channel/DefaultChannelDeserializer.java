package uk.co.idv.json.channel;

import uk.co.idv.json.channel.converter.AllSimpleChannelJsonNodeConverter;

import java.util.Collection;
import java.util.Collections;

public class DefaultChannelDeserializer extends ChannelDeserializer {

    public DefaultChannelDeserializer() {
        super(buildConverters());
    }

    private static Collection<ChannelJsonNodeConverter> buildConverters() {
        return Collections.singleton(new AllSimpleChannelJsonNodeConverter());
    }

}
