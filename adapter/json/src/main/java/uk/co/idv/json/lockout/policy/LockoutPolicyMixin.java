package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

public interface LockoutPolicyMixin {

    @JsonIgnore
    boolean isAliasLevel();

    @JsonIgnore
    String getRecordAttemptStrategyType();

}
