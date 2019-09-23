package uk.co.mruoc.jsonapi.error;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsonApiMultipleErrorDocument implements JsonApiErrorDocument {

    private final Collection<JsonApiErrorItem> errors;

    public JsonApiMultipleErrorDocument(final JsonApiErrorItem... error) {
        this(Arrays.asList(error));
    }

    public JsonApiMultipleErrorDocument(final Collection<JsonApiErrorItem> errors) {
        this.errors = errors;
    }

    @Override
    public Collection<JsonApiErrorItem> getErrors() {
        return errors;
    }

    @Override
    public int getStatus() {
        final List<Integer> distinctStatusValues = getDistinctStatusValues();
        if (distinctStatusValues.size() == 1) {
            return distinctStatusValues.get(0);
        }
        return 207;
    }

    private List<Integer> getDistinctStatusValues() {
        return errors.stream()
                .map(JsonApiErrorItem::getStatus)
                .distinct()
                .collect(Collectors.toList());
    }

}
