package uk.co.idv.json.channel;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;

@Slf4j
public class ChannelModule extends SimpleModule {

    private static final String NAME = "channel-module";

    public ChannelModule() {
        this(new DefaultChannelDeserializer());
    }

    public ChannelModule(final ChannelDeserializer deserializer) {
        super(NAME, Version.unknownVersion());

        log.info("creating {} with deserializer {}", NAME, deserializer);
        addDeserializer(Channel.class, deserializer);
    }

}
