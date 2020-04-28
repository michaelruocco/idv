package uk.co.idv.domain.entities.policy;

import java.util.Collection;

public interface PolicyLevel {

    String ALL = "all";

    boolean appliesTo(PolicyRequest request);

    String getChannelId();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

    boolean isAliasLevel();

}
