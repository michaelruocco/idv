package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

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
