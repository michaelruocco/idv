package uk.co.mruoc.jsonapi;

import java.util.UUID;

public class JsonApiDocumentWithId<T> {

    private final JsonApiDataItemWithId<T> data;

    public JsonApiDocumentWithId(final UUID id, final String type, final T attributes) {
        this.data = new JsonApiDataItemWithId<>(id, type, attributes);
    }

    public JsonApiDataItemWithId<T> getData() {
        return data;
    }

    public UUID getId() {
        return data.getId();
    }

    public T getAttributes() {
        return data.getAttributes();
    }

}
