package uk.co.idv.api.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LockoutLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
