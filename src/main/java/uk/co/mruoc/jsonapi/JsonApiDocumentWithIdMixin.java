package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface JsonApiDocumentWithIdMixin<T> {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    T getAttributes();

}
