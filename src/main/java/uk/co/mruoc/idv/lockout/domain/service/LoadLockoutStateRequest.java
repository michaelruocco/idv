package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.time.Instant;
import java.util.UUID;

@Builder
public class LoadLockoutStateRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias alias;
    private final Instant timestamp;

    private final UUID idvIdValue;

    public String getChannelId() {
        return channel.getId();
    }

    public String getActivityName() {
        return activity.getName();
    }

    public Alias getAlias() {
        return alias;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public UUID getIdvIdValue() {
        return idvIdValue;
    }

}
