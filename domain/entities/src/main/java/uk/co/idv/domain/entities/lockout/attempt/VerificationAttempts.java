package uk.co.idv.domain.entities.lockout.attempt;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return update(newAttempts);
        }
        throw new CannotAddAttemptException(idvId, attempt.getIdvIdValue());
    }

    public Collection<VerificationAttempt> collection() {
        return Collections.unmodifiableCollection(attempts);
    }

    public int size() {
        return attempts.size();
    }

    public VerificationAttempts update(final Collection<VerificationAttempt> newAttempts) {
        return new VerificationAttempts(id, idvId, newAttempts);
    }

    public VerificationAttempts filterMatching(final LockoutLevel level) {
        final Collection<VerificationAttempt> applicableAttempts = attempts.stream()
                .filter(level::appliesTo)
                .collect(Collectors.toList());
        return update(applicableAttempts);
    }

    public VerificationAttempts filterMatching(final Alias alias) {
        final Collection<VerificationAttempt> applicableAttempts = attempts.stream()
                .filter(attempt -> attempt.getAlias().equals(alias))
                .collect(Collectors.toList());
        return update(applicableAttempts);
    }

    public VerificationAttempts remove(final VerificationAttempts remove) {
        final Collection<VerificationAttempt> updatedAttempts = CollectionUtils.removeAll(attempts, remove.attempts);
        return update(updatedAttempts);
    }

    public Stream<VerificationAttempt> stream() {
        return attempts.stream();
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
