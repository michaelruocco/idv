package uk.co.idv.domain.entities.policy;

import java.util.UUID;

public interface Policy {

    UUID getId();

    PolicyLevel getLevel();

    default boolean isAliasLevel() {
        return getLevel().isAliasLevel();
    }

    default boolean appliesTo(final PolicyRequest request) {
        return getLevel().appliesTo(request);
    }

}
