package uk.co.mruoc.idv.mongo.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;


public class AliasDocumentConverter {

    private final AliasFactory aliasFactory;

    public AliasDocumentConverter(final AliasFactory aliasFactory) {
        this.aliasFactory = aliasFactory;
    }

    public Alias toAlias(final AliasDocument document) {
        return aliasFactory.build(document.getType(), document.getValue());
    }

    public AliasDocument toDocument(final Alias alias) {
        final AliasDocument document = new AliasDocument();
        document.setType(alias.getType());
        document.setValue(alias.getValue());
        return document;
    }

}
