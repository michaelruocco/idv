package uk.co.mruoc.idv.lockout.jsonapi;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

@Builder
@Getter
@EqualsAndHashCode
public class ResetLockoutStateAttributes implements LockoutRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

}
