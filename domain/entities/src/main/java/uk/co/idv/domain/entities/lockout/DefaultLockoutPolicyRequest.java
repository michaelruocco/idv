package uk.co.idv.domain.entities.lockout;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class DefaultLockoutPolicyRequest implements LockoutPolicyRequest {

    private final String channelId;
    private final String activityName;
    private final String aliasType;

}
