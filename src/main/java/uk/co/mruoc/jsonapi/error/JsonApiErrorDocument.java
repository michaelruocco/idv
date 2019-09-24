package uk.co.mruoc.jsonapi.error;


import java.util.Collection;

public interface JsonApiErrorDocument {

    Collection<JsonApiErrorItem> getErrors();

    int getStatus();

}
