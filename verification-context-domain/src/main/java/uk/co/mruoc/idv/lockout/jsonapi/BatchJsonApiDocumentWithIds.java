package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.jsonapi.JsonApiDataItem;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.Collection;
import java.util.stream.Collectors;

public class BatchJsonApiDocumentWithIds<T> {

    private final Collection<JsonApiDataItemWithId<T>> data;
    private final String type;

    public BatchJsonApiDocumentWithIds(final String type, final Collection<JsonApiDataItemWithId<T>> data) {
        this.data = data;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Collection<T> getAttributes() {
        return data.stream()
                .map(JsonApiDataItem::getAttributes)
                .collect(Collectors.toList());
    }

}
