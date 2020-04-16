package uk.co.idv.domain.usecases.identity.data;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

public class AliasLoader {

    public Aliases load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (shouldCreateAdditionalAlias(providedAlias)) {
            return Aliases.with(providedAlias, createAdditionalAlias(providedAlias));
        }
        return Aliases.with(providedAlias);
    }

    private boolean shouldCreateAdditionalAlias(final Alias providedAlias) {
        return providedAlias.getValue().endsWith("2");
    }

    private Alias createAdditionalAlias(final Alias providedAlias) {
        final String value = Long.toString(Long.parseLong(providedAlias.getValue()) + 1);
        return new CreditCardNumber(value);
    }

}
