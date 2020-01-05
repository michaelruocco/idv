package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LockoutLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
