package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;

public class FakeLockoutLevel implements LockoutLevel {

    private boolean appliesTo;
    private boolean includesAlias;

    @Override
    public String getType() {
        return "fake-type";
    }

    @Override
    public String getChannelId() {
        return "fake-channel-id";
    }

    @Override
    public String getActivityName() {
        return "fake-activity-name";
    }

    @Override
    public boolean appliesTo(LockoutRequest request) {
        return appliesTo;
    }

    @Override
    public boolean includesAlias() {
        return includesAlias;
    }

    public void setAppliesTo(boolean appliesTo) {
        this.appliesTo = appliesTo;
    }

    public void setIncludesAlias(boolean includesAlias) {
        this.includesAlias = includesAlias;
    }

}
