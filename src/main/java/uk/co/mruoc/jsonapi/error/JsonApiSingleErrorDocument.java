package uk.co.mruoc.jsonapi.error;

import java.util.Collection;
import java.util.Collections;

public class JsonApiSingleErrorDocument implements JsonApiErrorDocument {

    private final JsonApiErrorItem error;

    public JsonApiSingleErrorDocument(final JsonApiErrorItem error) {
        this.error = error;
    }

    @Override
    public Collection<JsonApiErrorItem> getErrors() {
        return Collections.singleton(error);
    }

    @Override
    public int getStatus() {
        return error.getStatus();
    }

}
