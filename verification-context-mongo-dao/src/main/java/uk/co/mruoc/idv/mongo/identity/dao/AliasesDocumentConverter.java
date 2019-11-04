package uk.co.mruoc.idv.mongo.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

public class AliasesDocumentConverter {

    private final AliasDocumentConverter aliasConverter;

    public AliasesDocumentConverter(final AliasDocumentConverter aliasConverter) {
        this.aliasConverter = aliasConverter;
    }

    public Aliases toAliases(final Collection<AliasDocument> documents) {
        final Collection<Alias> collection = documents.stream()
                .map(aliasConverter::toAlias)
                .collect(Collectors.toList());
        return Aliases.with(collection);
    }

    public Collection<AliasDocument> toDocuments(final Aliases aliases) {
        return aliases.stream()
                .map(aliasConverter::toDocument)
                .collect(Collectors.toList());
    }

}
