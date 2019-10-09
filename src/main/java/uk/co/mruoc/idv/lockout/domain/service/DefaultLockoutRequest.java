package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.identity.domain.model.Alias;

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
