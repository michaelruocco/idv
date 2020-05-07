package uk.co.idv.domain.entities.policy;

import java.util.UUID;

public interface Policy {

    UUID getId();

    boolean isAliasLevel();

    PolicyLevel getLevel();

    boolean appliesTo(final PolicyRequest request);

}
