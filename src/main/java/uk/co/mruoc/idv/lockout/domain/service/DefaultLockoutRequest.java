package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;

@Builder
@Getter
public class DefaultLockoutRequest implements LockoutRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

}
