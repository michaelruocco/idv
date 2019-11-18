package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

public class FakeLockoutPolicyParameters implements LockoutPolicyParameters {

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getRecordAttempts() {
        return null;
    }

    @Override
    public String getLockoutType() {
        return null;
    }

    @Override
    public LockoutLevel getLockoutLevel() {
        return null;
    }

}
