package uk.co.mruoc.idv.mongo.identity.dao;

import java.util.UUID;

public class FakeIdvIdAliasDocument extends AliasDocument {

    public FakeIdvIdAliasDocument() {
        this(UUID.randomUUID().toString());
    }

    public FakeIdvIdAliasDocument(final String value) {
        setType("idv-id");
        setValue(value);
    }

}
