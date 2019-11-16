package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;

public interface LockoutLevel {

    String getType();

    boolean appliesTo(LockoutRequest request);

    String getChannelId();

    String getActivityName();

    boolean isAliasLevel();

}
