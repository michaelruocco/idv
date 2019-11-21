package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.Collections;

public class FakeLockoutLevel implements LockoutLevel {

    private boolean appliesTo;
    private boolean includesAlias;

    @Override
    public String getChannelId() {
        return "fake-channel-id";
    }

    @Override
    public Collection<String> getActivityNames() {
        return Collections.singleton("fake-activity-name");
    }

    @Override
    public Collection<String> getAliasTypes() {
        return Collections.singleton("fake-alias-type");
    }

    @Override
    public boolean appliesTo(LockoutRequest request) {
        return appliesTo;
    }

    @Override
    public boolean isAliasLevel() {
        return includesAlias;
    }

    public void setAppliesTo(boolean appliesTo) {
        this.appliesTo = appliesTo;
    }

    public void setIncludesAlias(boolean includesAlias) {
        this.includesAlias = includesAlias;
    }

}
