package uk.co.mruoc.idv.mongo.identity.dao;

public class FakeIdvIdAliasDocument extends AliasDocument {

    public FakeIdvIdAliasDocument(final String value) {
        setType("idv-id");
        setValue(value);
    }

}
