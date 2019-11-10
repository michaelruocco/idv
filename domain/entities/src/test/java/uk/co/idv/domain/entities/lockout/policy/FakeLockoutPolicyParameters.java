package uk.co.idv.domain.entities.lockout.policy;

import java.util.UUID;

public class FakeLockoutPolicyParameters implements LockoutPolicyParameters {

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getRecordAttemptStrategyType() {
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
