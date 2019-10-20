package uk.co.mruoc.idv.mongo.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;

public class IdentityConverter {

    private final AliasesConverter aliasesConverter;

    public IdentityConverter(final AliasesConverter aliasesConverter) {
        this.aliasesConverter = aliasesConverter;
    }

    public Identity toIdentity(final IdentityDocument document) {
        final Aliases aliases = aliasesConverter.toAliases(document.getAliases());
        return new Identity(aliases);
    }

    public IdentityDocument toDocument(final Identity identity) {
        final IdentityDocument document = new IdentityDocument();
        document.setId(identity.getIdvIdValue().toString());
        document.setAliases(aliasesConverter.toDocuments(identity.getAliases()));
        return document;
    }

}
