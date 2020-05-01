package uk.co.idv.domain.entities.policy;


import java.util.Collection;
import java.util.Collections;

public class FakePolicyLevel implements PolicyLevel {

    private boolean appliesTo;
    private boolean aliasLevel;

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
    public boolean appliesTo(PolicyRequest request) {
        return appliesTo;
    }

    @Override
    public boolean isAliasLevel() {
        return aliasLevel;
    }

    public void setAppliesTo(boolean appliesTo) {
        this.appliesTo = appliesTo;
    }

    public void setAliasLevel(boolean aliasLevel) {
        this.aliasLevel = aliasLevel;
    }

}