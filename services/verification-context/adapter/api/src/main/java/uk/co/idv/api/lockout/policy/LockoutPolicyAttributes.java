package uk.co.idv.api.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

public interface LockoutPolicyAttributes {

    UUID getId();

    String getRecordAttempts();

    String getType();

    LockoutLevel getLevel();

}
