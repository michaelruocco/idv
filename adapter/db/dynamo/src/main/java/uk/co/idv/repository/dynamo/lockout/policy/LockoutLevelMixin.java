package uk.co.idv.repository.dynamo.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LockoutLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
