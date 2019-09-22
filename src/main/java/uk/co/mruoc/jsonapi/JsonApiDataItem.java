package uk.co.mruoc.jsonapi;

import lombok.Getter;

@Getter
public class JsonApiDataItem<T> {

    private final String type;
    private final T attributes;

    public JsonApiDataItem(final String type, final T attributes) {
        this.type = type;
        this.attributes = attributes;
    }

}
