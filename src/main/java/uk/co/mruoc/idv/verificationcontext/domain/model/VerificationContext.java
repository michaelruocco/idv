package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

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
    private final LockoutState lockoutState;

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

    public UUID getIdvIdValue() {
        return identity.getIdvIdValue();
    }

    public Activity getActivity() {
        return activity;
    }

    public String getActivityName() {
        return activity.getName();
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

    public LockoutState getLockoutState() {
        return lockoutState;
    }

    public VerificationContext addResult(final VerificationResult result) {
        final VerificationSequences updatedSequences = this.sequences.addResultIfHasSequencesWithNextMethod(result);
        return copyBuilder()
                .sequences(updatedSequences)
                .build();
    }

    public VerificationContext updateLockoutState(final LockoutState lockoutState) {
        return copyBuilder()
                .lockoutState(lockoutState)
                .build();
    }

    private VerificationContextBuilder copyBuilder() {
        return VerificationContext.builder()
                .id(id)
                .channel(channel)
                .providedAlias(providedAlias)
                .identity(identity)
                .activity(activity)
                .created(created)
                .expiry(expiry)
                .sequences(sequences)
                .lockoutState(lockoutState);
    }

}
