package uk.co.mruoc.idv.mongo.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;

public class IdentityConverter {

    private final AliasConverter aliasConverter;

    public IdentityConverter(final AliasConverter aliasConverter) {
        this.aliasConverter = aliasConverter;
    }

    public Identity toIdentity(final IdentityDocument document) {
        final Aliases aliases = aliasConverter.toAliases(document.getAliases());
        return new Identity(aliases);
    }

    public IdentityDocument toDocument(final Identity identity) {
        final IdentityDocument document = new IdentityDocument();
        document.setId(identity.getIdvIdValue().toString());
        document.setAliases(aliasConverter.toDocuments(identity.getAliases()));
        return document;
    }

}