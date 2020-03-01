package uk.co.idv.domain.entities.verification.onetimepasscode.exception;

import java.util.UUID;

public class NoAttemptsRemainingException extends RuntimeException {

    public NoAttemptsRemainingException(final UUID id, final int maxAttempts) {
        this(toMessage(id, maxAttempts));
    }

    public NoAttemptsRemainingException(final String message) {
        super(message);
    }

    private static String toMessage(final UUID id, final int maxAttempts) {
        return String.format("verification id %s, max attempts %d", id, maxAttempts);
    }

}
