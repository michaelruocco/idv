package uk.co.idv.repository.mongo.identity;

import uk.co.idv.repository.mongo.identity.alias.AliasesDocumentConverter;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;

public class IdentityDocumentConverter {

    private final AliasesDocumentConverter aliasesConverter;

    public IdentityDocumentConverter(final AliasesDocumentConverter aliasesConverter) {
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
