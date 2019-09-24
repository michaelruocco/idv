package uk.co.mruoc.jsonapi.error;

import java.util.Map;
import java.util.UUID;

class FakeInternalServerErrorItemWithMeta extends InternalServerErrorItem {

    private static final UUID ID = UUID.fromString("74a8a129-a96b-4979-98c1-90d864dd47a4");
    private static final String DETAIL = "faked detail message";
    private static final Map<String, Object> META = Map.of(
            "string-value", "my-value-1",
            "numeric-value", 999
    );

    FakeInternalServerErrorItemWithMeta() {
        super(ID, DETAIL, META);
    }

}
