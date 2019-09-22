package uk.co.mruoc.jsonapi;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JsonApiDataItem<T> {

    private final String type;
    private final T attributes;

    public JsonApiDataItem(final String type, final T attributes) {
        this.type = type;
        this.attributes = attributes;
    }

}
