package uk.co.mruoc.jsonapi.error;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public interface JsonApiErrorDocument {

    Collection<JsonApiErrorItem> getErrors();

    int getStatus();

}
