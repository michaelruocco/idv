package uk.co.mruoc.idv.mongo.identity.dao;

import java.util.Arrays;
import java.util.UUID;

public class FakeIdentityDocument extends IdentityDocument {

    public FakeIdentityDocument() {
        this(UUID.randomUUID().toString());
    }

    public FakeIdentityDocument(final String idvIdValue) {
        setId(idvIdValue);
        setAliases(Arrays.asList(
                new FakeIdvIdAliasDocument(idvIdValue),
                new FakeCreditCardNumberAliasDocument()
        ));
    }

}
