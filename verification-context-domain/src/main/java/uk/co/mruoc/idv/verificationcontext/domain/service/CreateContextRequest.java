package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;

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