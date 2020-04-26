package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;

public interface LockoutPolicyMixin {

    @JsonProperty("recordAttempts")
    RecordAttemptStrategy getRecordAttemptStrategy();

    @JsonIgnore
    boolean isAliasLevel();

    @JsonIgnore
    String getRecordAttemptStrategyType();

    @JsonIgnore
    String getChannelId();

}
