package uk.co.idv.domain.entities.lockout.policy;

import java.util.UUID;

//TODO remove this from entities and move up to api layer, rename to LockoutPolicyAttributes and move
//converter up to API layer too
public interface LockoutPolicyParameters {

    UUID getId();

    String getRecordAttemptStrategyType();

    String getLockoutType();

    LockoutLevel getLockoutLevel();

}
