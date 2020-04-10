package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;

import java.util.Collection;

public interface LockoutLevel {

    String ALL = "all";

    boolean appliesTo(LockoutPolicyRequest request);

    String getChannelId();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

    boolean isAliasLevel();

}
