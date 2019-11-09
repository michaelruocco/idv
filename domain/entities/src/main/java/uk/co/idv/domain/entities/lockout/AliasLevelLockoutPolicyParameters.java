package uk.co.idv.domain.entities.lockout;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AliasLevelLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

    private final String aliasType;

    public AliasLevelLockoutPolicyParameters(final UUID id,
                                             final String lockoutType,
                                             final String recordAttemptStrategyType,
                                             final String channelId,
                                             final String activityName,
                                             final String aliasType) {
        super(id, lockoutType, recordAttemptStrategyType, channelId, activityName);
        this.aliasType = aliasType;
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return super.appliesTo(request) && matchesAliasType(request);
    }

    @Override
    public boolean isAliasLevel() {
        return true;
    }

    private boolean matchesAliasType(final LockoutRequest request) {
        return aliasType.equals(request.getAliasType());
    }

}
