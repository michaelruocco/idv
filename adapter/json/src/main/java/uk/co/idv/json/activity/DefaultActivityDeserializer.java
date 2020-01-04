package uk.co.idv.json.activity;

import java.util.Arrays;
import java.util.Collection;

public class DefaultActivityDeserializer extends ActivityDeserializer {

    public DefaultActivityDeserializer() {
        super(buildConverters());
    }

    private static Collection<ActivityJsonNodeConverter> buildConverters() {
        return Arrays.asList(
                new OnlinePurchaseJsonNodeConverter(),
                new LoginJsonNodeConverter()
        );
    }

}
