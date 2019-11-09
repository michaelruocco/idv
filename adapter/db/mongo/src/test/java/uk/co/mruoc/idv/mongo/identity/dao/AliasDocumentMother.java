package uk.co.mruoc.idv.mongo.identity.dao;

import java.util.UUID;

public class AliasDocumentMother {

    public static AliasDocument creditCard() {
        final AliasDocument document = new AliasDocument();
        document.setType("credit-card-number");
        document.setValue("4929001111111111");
        return document;
    }

    public static AliasDocument idvId() {
        return idvId(UUID.randomUUID().toString());
    }

    public static AliasDocument idvId(final String value) {
        final AliasDocument document = new AliasDocument();
        document.setType("idv-id");
        document.setValue(value);
        return document;
    }

}
