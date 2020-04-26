package uk.co.idv.api.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

public class FakeLockoutPolicyAttributes implements LockoutPolicyAttributes {

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getRecordAttempts() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public LockoutLevel getLevel() {
        return null;
    }

}
