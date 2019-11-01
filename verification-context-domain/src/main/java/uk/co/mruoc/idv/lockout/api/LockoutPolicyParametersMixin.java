package uk.co.mruoc.idv.lockout.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface LockoutPolicyParametersMixin {

    @JsonProperty("type")
    String getLockoutType();

    @JsonProperty("recordAttemptStrategy")
    String getRecordAttemptStrategyType();

}
