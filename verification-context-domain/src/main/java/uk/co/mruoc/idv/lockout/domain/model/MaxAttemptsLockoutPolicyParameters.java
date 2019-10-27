package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class MaxAttemptsLockoutPolicyParameters extends LockoutPolicyParameters {

    public static final String TYPE = "max-attempts";

    private final int maxNumberOfAttempts;

    @Builder
    public MaxAttemptsLockoutPolicyParameters(final UUID id,
                                              final String recordAttemptStrategyType,
                                              final Collection<String> channelIds,
                                              final Collection<String> activityNames,
                                              final Collection<String> aliasTypes,
                                              final int maxNumberOfAttempts) {
        super(id, TYPE, recordAttemptStrategyType, channelIds, activityNames, aliasTypes);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

}
