package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.UUID;

public class FakeLockoutPolicyParameters implements LockoutPolicyParameters {

    private boolean aliasLevelLocking;
    private boolean appliesTo;

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
    public boolean appliesTo(LockoutRequest request) {
        return appliesTo;
    }

    @Override
    public boolean isAliasLevel() {
        return aliasLevelLocking;
    }

    @Override
    public String getChannelId() {
        return null;
    }

    @Override
    public String getActivityName() {
        return null;
    }

    public void setAliasLevelLocking(boolean aliasLevelLocking) {
        this.aliasLevelLocking = aliasLevelLocking;
    }

    public void setAppliesTo(boolean appliesTo) {
        this.appliesTo = appliesTo;
    }

}
