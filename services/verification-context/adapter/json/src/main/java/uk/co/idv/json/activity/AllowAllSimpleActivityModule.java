package uk.co.idv.json.activity;

import uk.co.idv.json.activity.converter.AllSimpleActivityJsonNodeConverter;

public class AllowAllSimpleActivityModule extends ActivityModule {

    public AllowAllSimpleActivityModule() {
        super(new ActivityDeserializer(new AllSimpleActivityJsonNodeConverter()));
    }

}
