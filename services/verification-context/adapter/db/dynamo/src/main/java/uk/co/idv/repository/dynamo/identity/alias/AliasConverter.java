package uk.co.idv.repository.dynamo.identity.alias;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;

@RequiredArgsConstructor
public class AliasConverter {

    private final AliasFactory factory;

    public String toString(final Alias alias) {
        return String.format("%s|%s", alias.getType(), alias.getValue());
    }

    public Alias toAlias(final String alias) {
        final String[] parts = alias.split("\\|");
        final String type = parts[0];
        final String value = parts[1];
        return factory.build(type, value);
    }

}
