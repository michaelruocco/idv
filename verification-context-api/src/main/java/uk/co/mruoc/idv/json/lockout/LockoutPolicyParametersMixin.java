package uk.co.mruoc.idv.json.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface LockoutPolicyParametersMixin {

    @JsonProperty("type")
    String getLockoutType();

    @JsonProperty("recordAttemptStrategy")
    String getRecordAttemptStrategyType();

    @JsonIgnore
    boolean isAliasLevel();

}
