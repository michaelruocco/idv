package uk.co.mruoc.idv.json.activity;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.activity.Activity;

public class ActivityModule extends SimpleModule {

    public ActivityModule() {
        setMixInAnnotation(Activity.class, ActivityMixin.class);
        addDeserializer(Activity.class, new ActivityDeserializer());
    }

}
