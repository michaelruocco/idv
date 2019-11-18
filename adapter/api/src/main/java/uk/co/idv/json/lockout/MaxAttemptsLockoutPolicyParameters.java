package uk.co.idv.json.lockout;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutStateCalculator;

import java.util.UUID;

@Getter
public class MaxAttemptsLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

    private final int maxNumberOfAttempts;

    @Builder
    public MaxAttemptsLockoutPolicyParameters(final UUID id,
                                              final String recordAttempts,
                                              final LockoutLevel lockoutLevel,
                                              final int maxNumberOfAttempts) {
        super(id,
                MaxAttemptsLockoutStateCalculator.TYPE,
                recordAttempts,
                lockoutLevel);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

}
