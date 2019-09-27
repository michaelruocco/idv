package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@ToString
public class VerificationAttempts implements Iterable<VerificationAttempt> {

    private final UUID idvId;
    private final Collection<VerificationAttempt> attempts;

    @Override
    public Iterator<VerificationAttempt> iterator() {
        return attempts.iterator();
    }

    public UUID getIdvId() {
        return idvId;
    }

    public VerificationAttempts add(final VerificationAttempt attempt) {
        if (idvId.equals(attempt.getIdvId())) {
            final Collection<VerificationAttempt> newAttempts = new ArrayList<>(attempts);
            newAttempts.add(attempt);
            return toAttempts(newAttempts);
        }
        throw new CannotAddAttemptException(idvId, attempt.getIdvId());
    }

    public VerificationAttempts reset() {
        return toAttempts(Collections.emptyList());
    }

    public VerificationAttempts resetByAlias(final Alias alias) {
        final VerificationAttempts attemptsWithAlias = getAttemptsWithAlias(alias);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithAlias);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts getAttemptsWithAlias(final Alias alias) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> attempt.getProvidedAlias().equals(alias))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    public VerificationAttempts resetByChannel(final String channelId) {
        final VerificationAttempts attemptsWithChannel = getAttemptsWithChannel(channelId);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithChannel);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts getAttemptsWithChannel(final String channelId) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> attempt.getChannelId().equals(channelId))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    public VerificationAttempts resetByActivity(final String activity) {
        final VerificationAttempts attemptsWithActivity = getAttemptsWithActivity(activity);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithActivity);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts getAttemptsWithActivity(final String activityName) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> attempt.getActivityName().equals(activityName))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    private VerificationAttempts toAttempts(final Collection<VerificationAttempt> newAttempts) {
        return VerificationAttempts.builder()
                .idvId(idvId)
                .attempts(newAttempts)
                .build();
    }

    @Getter
    public static class CannotAddAttemptException extends RuntimeException {

        private final UUID idvId;
        private final UUID attemptIdvId;

        public CannotAddAttemptException(final UUID idvId, final UUID attemptIdvId) {
            super(String.format("attempt idv id %s does not match attempts idv id %s", attemptIdvId, idvId));
            this.idvId = idvId;
            this.attemptIdvId = attemptIdvId;
        }

    }

}
