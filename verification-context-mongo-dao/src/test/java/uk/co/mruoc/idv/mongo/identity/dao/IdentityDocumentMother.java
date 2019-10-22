package uk.co.mruoc.idv.mongo.identity.dao;

import java.util.Arrays;
import java.util.UUID;

public class IdentityDocumentMother {

    public static IdentityDocument build() {
        return build(UUID.randomUUID().toString());
    }

    public static IdentityDocument build(final String idvIdValue) {
        final IdentityDocument document = new IdentityDocument();
        document.setId(idvIdValue);
        document.setAliases(Arrays.asList(
                AliasDocumentMother.idvId(idvIdValue),
                AliasDocumentMother.creditCard()
        ));
        return document;
    }

}
