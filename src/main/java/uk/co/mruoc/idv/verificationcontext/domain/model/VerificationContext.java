package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContext {

    private final UUID id;
    private final Channel channel;
    private final Alias providedAlias;
    private final Identity identity;
    private final Activity activity;
    private final Instant created;
    private final Instant expiry;
    private final VerificationSequences sequences;

    public UUID getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getChannelId() {
        return channel.getId();
    }

    public Alias getProvidedAlias() {
        return providedAlias;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public VerificationSequences getSequences() {
        return sequences;
    }

}
