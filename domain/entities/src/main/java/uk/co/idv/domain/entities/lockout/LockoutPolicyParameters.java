package uk.co.idv.domain.entities.lockout;

import java.util.UUID;

public interface LockoutPolicyParameters {

    UUID getId();

    String getRecordAttemptStrategyType();

    String getLockoutType();

    boolean appliesTo(LockoutRequest request);

    boolean isAliasLevel();

    String getChannelId();

    String getActivityName();

}
