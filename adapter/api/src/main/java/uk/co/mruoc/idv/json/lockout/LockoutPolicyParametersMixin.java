package uk.co.mruoc.idv.json.lockout;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

public interface LockoutPolicyParametersMixin {

    @JsonProperty("level")
    LockoutLevel getLockoutLevel();

    @JsonProperty("recordAttemptStrategy")
    String getRecordAttemptStrategyType();

    @JsonProperty("type")
    String getLockoutType();

}
