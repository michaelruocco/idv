package uk.co.idv.domain.entities.identity;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;

import java.util.UUID;

public class Identity {

    private final Aliases aliases;

    public Identity(final Aliases aliases) {
        this.aliases = aliases;
    }

    public UUID getIdvIdValue() {
        return aliases.getIdvIdValue();
    }

    public Aliases getAliases() {
        return aliases;
    }

    public boolean hasAlias(final Alias alias) {
        return aliases.contains(alias);
    }

}
