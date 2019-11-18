package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

//TODO rename to LockoutPolicyDto
public interface LockoutPolicyParameters {

    UUID getId();

    String getRecordAttempts();

    String getLockoutType();

    LockoutLevel getLockoutLevel();

}
