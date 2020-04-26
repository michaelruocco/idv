package uk.co.idv.domain.entities.onetimepasscode;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.VerificationStatus;
import uk.co.idv.domain.entities.onetimepasscode.exception.NoDeliveriesRemainingException;
import uk.co.idv.domain.entities.onetimepasscode.exception.VerificationAlreadyCompleteException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

//TODO try making this immutable
@Builder
@Getter
public class OneTimePasscodeVerification {

    private final UUID id;
    private final UUID contextId;
    private final Instant created;
    private final Instant expiry;
    private final int maxDeliveries;
    private final int maxAttempts;

    @Builder.Default
    private final Collection<OneTimePasscodeDelivery> deliveries = new ArrayList<>();

    @Builder.Default
    private final Collection<OneTimePasscodeVerificationAttempt> attempts = new ArrayList<>();

    @Builder.Default
    private String status = VerificationStatus.PENDING;

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

    public void record(final OneTimePasscodeDelivery delivery) {
        validateComplete();
        if (!hasDeliveriesRemaining()) {
            throw new NoDeliveriesRemainingException(id, getMaxDeliveries());
        }
        this.deliveries.add(delivery);
    }

    public int getDeliveriesRemaining() {
        return maxDeliveries - deliveries.size();
    }

    public void verify(final Collection<OneTimePasscodeVerificationAttempt> attempts) {
        attempts.forEach(this::verify);
    }

    public void verify(final OneTimePasscodeVerificationAttempt attempt) {
        validateComplete();
        if (isValid(attempt)) {
            handleSuccessful(attempt);
            return;
        }
        handleFailed(attempt);
    }

    public boolean isSuccessful() {
        return VerificationStatus.SUCCESSFUL.equals(status);
    }

    public int getAttemptsRemaining() {
        return maxAttempts - attempts.size();
    }

    private void validateComplete() {
        if (isComplete()) {
            throw new VerificationAlreadyCompleteException(id);
        }
    }

    private boolean hasDeliveriesRemaining() {
        return getDeliveriesRemaining() > 0;
    }

    private boolean hasAttemptsRemaining() {
        return getAttemptsRemaining() > 0;
    }

    private boolean isValid(final OneTimePasscodeVerificationAttempt attempt) {
        return deliveries.stream().anyMatch(delivery -> delivery.hasPasscode(attempt.getPasscode()));
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
