package uk.co.mruoc.idv.mongo.identity.dao;

public class FakeCreditCardNumberAliasDocument extends AliasDocument {

    public FakeCreditCardNumberAliasDocument() {
        setType("credit-card-number");
        setValue("4929001111111111");
    }

}
