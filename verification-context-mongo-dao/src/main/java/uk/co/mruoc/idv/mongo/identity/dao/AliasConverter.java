package uk.co.mruoc.idv.mongo.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.model.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

public class AliasConverter {

    private final AliasFactory aliasFactory;

    public AliasConverter(final AliasFactory aliasFactory) {
        this.aliasFactory = aliasFactory;
    }

    public Aliases toAliases(final Collection<AliasDocument> documents) {
        final Collection<Alias> collection = documents.stream()
                .map(this::toAlias)
                .collect(Collectors.toList());
        return Aliases.with(collection);
    }

    public Alias toAlias(final AliasDocument document) {
        return aliasFactory.build(document.getType(), document.getValue());
    }

    public Collection<AliasDocument> toDocuments(final Aliases aliases) {
        return aliases.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public AliasDocument toDocument(final Alias alias) {
        return AliasDocument.builder()
                .type(alias.getType())
                .value(alias.getValue())
                .build();
    }

}
