package uk.co.idv.domain.entities.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

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

    public boolean hasExpired(final Instant now) {
        return now.isAfter(expiry);
    }

    public boolean hasChannelId(final String channelId) {
        return channel.hasId(channelId);
    }

    public VerificationContext addResult(final VerificationResult result) {
        final VerificationSequences updatedSequences = this.sequences.addResultIfHasSequencesWithNextMethod(result);
        return VerificationContext.builder()
                .id(id)
                .channel(channel)
                .providedAlias(providedAlias)
                .identity(identity)
                .activity(activity)
                .created(created)
                .expiry(expiry)
                .sequences(updatedSequences)
                .build();
    }

    public boolean containsCompleteSequenceContainingMethod(final String methodName) {
        return sequences.containsCompleteSequenceContainingMethod(methodName);
    }

    public boolean containsCompleteMethod(final String methodName) {
        return sequences.containsCompleteMethod(methodName);
    }

}
