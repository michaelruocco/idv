package uk.co.idv.domain.entities.lockout;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultLockoutPolicyRequest implements LockoutPolicyRequest {

    private final String channelId;
    private final String activityName;
    private final String aliasType;

}
