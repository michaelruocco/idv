package uk.co.mruoc.jsonapi;


public class JsonApiDocument<T> {

    private final JsonApiDataItem<T> data;

    public JsonApiDocument(final String type, final T attributes) {
        this.data = new JsonApiDataItem<>(type, attributes);
    }

    public String getType() {
        return data.getType();
    }

    public T getAttributes() {
        return data.getAttributes();
    }

}
