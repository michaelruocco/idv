package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.verification.VerificationStatus;
import uk.co.idv.domain.entities.verification.onetimepasscode.exception.NoGenerationsRemainingException;
import uk.co.idv.domain.entities.verification.onetimepasscode.exception.VerificationAlreadyCompleteException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
public class OneTimePasscodeVerification {

    private final UUID id;
    private final Instant created;
    private final Instant expiry;
    private final String passcode;
    private final int maxGenerations;
    private final int maxAttempts;

    @Builder.Default
    private String status = VerificationStatus.PENDING;

    @Builder.Default
    private Collection<OneTimePasscodeGeneration> generations = new ArrayList<>();

    @Builder.Default
    private Collection<OneTimePasscodeVerificationAttempt> attempts = new ArrayList<>();

    private Instant completed;

    public boolean hasExpired(final Instant now) {
        return now.isAfter(expiry);
    }

    public boolean isComplete() {
        return getCompleted().isPresent();
    }

    public Optional<Instant> getCompleted() {
        return Optional.ofNullable(completed);
    }

    public void record(final OneTimePasscodeGeneration generation) {
        validateComplete();
        if (!hasGenerationsRemaining()) {
            throw new NoGenerationsRemainingException(id, getMaxGenerations());
        }
        this.generations.add(generation);
    }

    public int getGenerationsRemaining() {
        return maxGenerations - generations.size();
    }

    public void verify(final OneTimePasscodeVerificationAttempt attempt) {
        validateComplete();
        if (isValid(attempt)) {
            handleSuccessful(attempt);
            return;
        }
        handleFailed(attempt);
    }

    public int getAttemptsRemaining() {
        return maxAttempts - attempts.size();
    }

    private void validateComplete() {
        if (isComplete()) {
            throw new VerificationAlreadyCompleteException(id);
        }
    }

    private boolean hasGenerationsRemaining() {
        return getGenerationsRemaining() > 0;
    }

    private boolean hasAttemptsRemaining() {
        return getAttemptsRemaining() > 0;
    }

    private boolean isValid(final OneTimePasscodeVerificationAttempt attempt) {
        return generations.stream().anyMatch(generation -> generation.isValid(attempt.getPasscode()));
    }

    private void handleSuccessful(final OneTimePasscodeVerificationAttempt attempt) {
        this.attempts.add(attempt);
        this.completed = attempt.getCreated();
        this.status = VerificationStatus.SUCCESSFUL;

    }

    private void handleFailed(final OneTimePasscodeVerificationAttempt attempt) {
        this.attempts.add(attempt);
        if (!hasAttemptsRemaining()) {
            this.completed = attempt.getCreated();
            this.status = VerificationStatus.FAILED;
        }
    }

}
