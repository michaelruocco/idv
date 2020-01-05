package uk.co.idv.repository.dynamo.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.UUID;

public interface LockoutPolicyMixin {

    @JsonIgnore
    LockoutStateCalculator getStateCalculator();

    @JsonIgnore
    boolean isAliasLevel();

    @JsonIgnore
    String getRecordAttemptStrategyType();

}
