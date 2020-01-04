package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.activity.Activity;

public class ActivityModule extends SimpleModule {

    public ActivityModule() {
        this(new DefaultActivityDeserializer());
    }

    public ActivityModule(final ActivityDeserializer activityDeserializer) {
        setMixInAnnotation(Activity.class, ActivityMixin.class);
        addDeserializer(Activity.class, activityDeserializer);
    }

}
