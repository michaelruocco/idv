package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;

public interface LockoutLevel {

    String ALL = "all";

    boolean appliesTo(LockoutRequest request);

    String getChannelId();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

    boolean isAliasLevel();

}
