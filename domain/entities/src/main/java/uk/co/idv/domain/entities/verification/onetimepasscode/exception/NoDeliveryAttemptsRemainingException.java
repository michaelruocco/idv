package uk.co.idv.domain.entities.verification.onetimepasscode.exception;

import java.util.UUID;

public class NoDeliveryAttemptsRemainingException extends RuntimeException {

    public NoDeliveryAttemptsRemainingException(final UUID id, final int maxDeliveryAttempts) {
        this(toMessage(id, maxDeliveryAttempts));
    }

    public NoDeliveryAttemptsRemainingException(final String message) {
        super(message);
    }

    private static String toMessage(final UUID id, final int maxDeliveryAttempts) {
        return String.format("verification id %s, max delivery attempts %d", id, maxDeliveryAttempts);
    }

}
