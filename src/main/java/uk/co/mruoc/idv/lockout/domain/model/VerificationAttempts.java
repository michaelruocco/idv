package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ToString
public class VerificationAttempts implements Iterable<VerificationAttempt> {

    private final UUID id;
    private final UUID idvId;
    private final Collection<VerificationAttempt> attempts;

    public VerificationAttempts(final UUID idvId) {
        this(UUID.randomUUID(), idvId);
    }

    public VerificationAttempts(final UUID id, final UUID idvId) {
        this(id, idvId, Collections.emptyList());
    }

    public VerificationAttempts(final UUID idvId, final Collection<VerificationAttempt> attempts) {
        this(UUID.randomUUID(), idvId, attempts);
    }

    public VerificationAttempts(final UUID id, final UUID idvId, final Collection<VerificationAttempt> attempts) {
        this.id = id;
        this.idvId = idvId;
        this.attempts = attempts;
    }

    @Override
    public Iterator<VerificationAttempt> iterator() {
        return attempts.iterator();
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdvId() {
        return idvId;
    }

    public VerificationAttempts add(final VerificationAttempt attempt) {
        if (idvId.equals(attempt.getIdvIdValue())) {
            final Collection<VerificationAttempt> newAttempts = new ArrayList<>(attempts);
            newAttempts.add(attempt);
            return toAttempts(newAttempts);
        }
        throw new CannotAddAttemptException(idvId, attempt.getIdvIdValue());
    }

    public VerificationAttempts reset() {
        return toAttempts(Collections.emptyList());
    }

    public VerificationAttempts resetByAlias(final Alias alias) {
        final VerificationAttempts attemptsWithAlias = getAttemptsWithAlias(alias);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithAlias);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts resetBy(final Predicate<LockoutPolicyRequest> filter) {
        final VerificationAttempts filteredAttempts = filterBy(filter);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, filteredAttempts);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts filterBy(final Predicate<LockoutPolicyRequest> filter) {
        final Collection<VerificationAttempt> filteredAttempts = attempts.stream()
                .filter(filter)
                .collect(Collectors.toList());
        return toAttempts(filteredAttempts);
    }

    public VerificationAttempts getAttemptsWithAlias(final Alias alias) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> alias.equals(attempt.getProvidedAlias()))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    public VerificationAttempts resetByChannel(final String channelId) {
        final VerificationAttempts attemptsWithChannel = getAttemptsWithChannel(channelId);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithChannel);
        return toAttempts(remainingAttempts);
    }

    public VerificationAttempts resetByActivity(final String activityName) {
        final VerificationAttempts attemptsWithActivity = getAttemptsWithActivity(activityName);
        final Collection<VerificationAttempt> remainingAttempts = CollectionUtils.subtract(attempts, attemptsWithActivity);
        return toAttempts(remainingAttempts);
    }

    public int size() {
        return attempts.size();
    }

    private VerificationAttempts getAttemptsWithChannel(final String channelId) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> channelId.equals(attempt.getChannelId()))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    private VerificationAttempts getAttemptsWithActivity(final String activityName) {
        Collection<VerificationAttempt> attemptsWithAlias = attempts.stream()
                .filter(attempt -> activityName.equals(attempt.getActivityName()))
                .collect(Collectors.toList());
        return toAttempts(attemptsWithAlias);
    }

    private VerificationAttempts toAttempts(final Collection<VerificationAttempt> newAttempts) {
        return new VerificationAttempts(id, idvId, newAttempts);
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
