package uk.co.mruoc.idv.identity.domain.model;

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

}
