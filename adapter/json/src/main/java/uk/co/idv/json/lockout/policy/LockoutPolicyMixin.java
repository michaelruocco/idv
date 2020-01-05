package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LockoutPolicyMixin {

    @JsonIgnore
    boolean isAliasLevel();

    @JsonIgnore
    String getRecordAttemptStrategyType();

}
