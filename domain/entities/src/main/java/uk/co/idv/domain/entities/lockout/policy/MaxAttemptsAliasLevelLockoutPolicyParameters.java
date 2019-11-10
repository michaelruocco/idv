package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MaxAttemptsAliasLevelLockoutPolicyParameters extends AliasLevelLockoutPolicyParameters {

    public static final String TYPE = "max-attempts";

    private final int maxNumberOfAttempts;

    @Builder
    public MaxAttemptsAliasLevelLockoutPolicyParameters(final UUID id,
                                                        final String recordAttemptStrategyType,
                                                        final String channelId,
                                                        final String activityName,
                                                        final String aliasType,
                                                        final int maxNumberOfAttempts) {
        super(id,
                TYPE,
                recordAttemptStrategyType,
                channelId,
                activityName,
                aliasType);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

}
