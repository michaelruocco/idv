package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.verification.VerificationStatus;
import uk.co.idv.domain.entities.verification.onetimepasscode.exception.NoDeliveryAttemptsRemainingException;
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
    private final UUID contextId;
    private final Instant created;
    private final Instant expiry;
    private final int maxDeliveryAttempts;
    private final int maxAttempts;

    @Builder.Default
    private String status = VerificationStatus.PENDING;

    @Builder.Default
    private Collection<OneTimePasscodeDelivery> deliveries = new ArrayList<>();

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

    public void record(final OneTimePasscodeDelivery delivery) {
        validateComplete();
        if (!hasDeliveriesRemaining()) {
            throw new NoDeliveryAttemptsRemainingException(id, getMaxDeliveryAttempts());
        }
        this.deliveries.add(delivery);
    }

    public int getDeliveryAttemptsRemaining() {
        return maxDeliveryAttempts - deliveries.size();
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

    private boolean hasDeliveriesRemaining() {
        return getDeliveryAttemptsRemaining() > 0;
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
