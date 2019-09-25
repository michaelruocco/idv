package uk.co.mruoc.jsonapi;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class JsonApiDataItemWithId<T> extends JsonApiDataItem<T> {

    private final UUID id;

    public JsonApiDataItemWithId(final UUID id, final String type, final T attributes) {
        super(type, attributes);
        this.id = id;
    }

}
