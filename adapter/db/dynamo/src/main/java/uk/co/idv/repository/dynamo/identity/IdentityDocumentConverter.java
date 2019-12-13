package uk.co.idv.repository.dynamo.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IdentityDocumentConverter {

    private final AliasConverter aliasConverter;

    public Collection<IdentityDocument> toDocuments(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        return aliases.stream()
                .map(alias -> toDocument(identity, alias))
                .collect(Collectors.toList());
    }

    private IdentityDocument toDocument(final Identity identity, final Alias alias) {
        final IdentityDocument document = new IdentityDocument();
        document.setIdvId(identity.getIdvIdValue().toString());
        document.setAlias(aliasConverter.toString(alias));
        return document;
    }

    public Identity toIdentity(final Collection<IdentityDocument> documents) {
        final Collection<Alias> aliases = documents.stream()
                .map(document -> aliasConverter.toAlias(document.getAlias()))
                .collect(Collectors.toList());
        return new Identity(Aliases.with(aliases));
    }

}
