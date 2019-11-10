package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MaxAttemptsLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

    public static final String TYPE = "max-attempts";

    private final int maxNumberOfAttempts;

    @Builder
    public MaxAttemptsLockoutPolicyParameters(final UUID id,
                                              final String recordAttemptStrategyType,
                                              final LockoutLevel lockoutLevel,
                                              final int maxNumberOfAttempts) {
        super(id,
                TYPE,
                recordAttemptStrategyType,
                lockoutLevel);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

}
