package uk.co.idv.json.lockout.policy.level;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LockoutLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
