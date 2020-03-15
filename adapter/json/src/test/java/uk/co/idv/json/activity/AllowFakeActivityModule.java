package uk.co.idv.json.activity;

import uk.co.idv.json.activity.simple.AllSimpleActivityJsonNodeConverter;

public class AllowFakeActivityModule extends ActivityModule {

    public AllowFakeActivityModule() {
        super(buildActivityDeserializer());
    }

    private static ActivityDeserializer buildActivityDeserializer() {
        return new ActivityDeserializer(
                new OnlinePurchaseJsonNodeConverter(),
                new LoginJsonNodeConverter(),
                new AllSimpleActivityJsonNodeConverter()
        );
    }

}
