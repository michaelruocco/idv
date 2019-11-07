package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
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
    public boolean useAliasLevelLocking() {
        return aliasLevelLocking;
    }

    @Override
    public Collection<String> getChannelIds() {
        return null;
    }

    @Override
    public Collection<String> getActivityNames() {
        return null;
    }

    @Override
    public Collection<String> getAliasTypes() {
        return null;
    }

    public void setAliasLevelLocking(boolean aliasLevelLocking) {
        this.aliasLevelLocking = aliasLevelLocking;
    }

    public void setAppliesTo(boolean appliesTo) {
        this.appliesTo = appliesTo;
    }

}
