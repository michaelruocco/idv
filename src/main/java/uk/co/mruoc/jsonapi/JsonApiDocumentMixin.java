package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface JsonApiDocumentMixin<T> {

    @JsonIgnore
    T getAttributes();

}
