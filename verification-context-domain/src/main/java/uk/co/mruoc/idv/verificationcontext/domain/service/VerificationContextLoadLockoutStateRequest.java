package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextLoadLockoutStateRequest implements LockoutStateRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias alias;
    private final Instant timestamp;
    private final UUID idvIdValue;

    @Override
    public String getChannelId() {
        return channel.getId();
    }

    @Override
    public String getActivityName() {
        return activity.getName();
    }

    @Override
    public Alias getAlias() {
        return alias;
    }

    @Override
    public String getAliasType() {
        return alias.getType();
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public UUID getIdvIdValue() {
        return idvIdValue;
    }

}
