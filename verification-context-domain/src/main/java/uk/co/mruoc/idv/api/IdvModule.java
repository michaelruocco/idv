package uk.co.mruoc.idv.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.api.activity.ActivityDeserializer;
import uk.co.mruoc.idv.api.activity.ActivityMixin;
import uk.co.mruoc.idv.api.channel.ChannelDeserializer;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;

public class IdvModule extends SimpleModule {

    public IdvModule() {
        setMixInAnnotation(Activity.class, ActivityMixin.class);

        addDeserializer(Channel.class, new ChannelDeserializer());
        addDeserializer(Activity.class, new ActivityDeserializer());
    }

}
