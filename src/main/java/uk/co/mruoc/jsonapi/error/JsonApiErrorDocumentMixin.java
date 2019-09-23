package uk.co.mruoc.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface JsonApiErrorDocumentMixin {

    @JsonIgnore
    int getStatus();

}
