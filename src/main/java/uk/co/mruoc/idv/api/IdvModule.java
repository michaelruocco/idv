package uk.co.mruoc.idv.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.domain.model.activity.Activity;

public class IdvModule extends SimpleModule {

    public IdvModule() {
        setMixInAnnotation(Activity.class, ActivityMixin.class);
    }

}
