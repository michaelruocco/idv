package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.Alias;

@Getter
@Builder
@EqualsAndHashCode
public class CreateContextRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;

    public String getChannelId() {
        return channel.getId();
    }

    public String getActivityName() {
        return activity.getName();
    }

}
