package uk.co.mruoc.idv.lockout.jsonapi;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;

@Builder
@Getter
public class ResetLockoutStateAttributes {

    private final String channelId;
    private final String activityName;
    private final Alias alias;

}
