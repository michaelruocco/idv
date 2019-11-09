package uk.co.idv.domain.entities.lockout;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.Alias;

@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class DefaultLockoutRequest implements LockoutRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

}
