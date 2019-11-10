package uk.co.idv.domain.entities.lockout.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class DefaultLockoutPolicyParameters implements LockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final String channelId;
    private final String activityName;

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return matchesChannel(request) && matchesActivityName(request);
    }

    @Override
    public boolean isAliasLevel() {
        return false;
    }

    private boolean matchesChannel(final LockoutRequest request) {
        return channelId.equals(request.getChannelId());
    }

    private boolean matchesActivityName(final LockoutRequest request) {
        return activityName.equals(request.getActivityName());
    }

}
