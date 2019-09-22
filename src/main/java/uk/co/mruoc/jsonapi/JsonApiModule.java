package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonApiModule extends SimpleModule {

    public JsonApiModule() {
        setMixInAnnotation(JsonApiDataItem.class, JsonApiDataItemMixin.class);
        setMixInAnnotation(JsonApiDataItemWithId.class, JsonApiDataItemWithIdMixin.class);

        setMixInAnnotation(JsonApiDocument.class, JsonApiDocumentMixin.class);
        setMixInAnnotation(JsonApiDocumentWithId.class, JsonApiDocumentWithIdMixin.class);
    }

}
