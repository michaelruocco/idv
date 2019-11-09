package uk.co.idv.repository.mongo.identity.alias;

import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasFactory;


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
