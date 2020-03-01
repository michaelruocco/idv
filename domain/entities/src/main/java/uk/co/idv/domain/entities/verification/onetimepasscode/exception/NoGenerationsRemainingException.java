package uk.co.idv.domain.entities.verification.onetimepasscode.exception;

import java.util.UUID;

public class NoGenerationsRemainingException extends RuntimeException {

    public NoGenerationsRemainingException(final UUID id, final int maxGenerations) {
        this(toMessage(id, maxGenerations));
    }

    public NoGenerationsRemainingException(final String message) {
        super(message);
    }

    private static String toMessage(final UUID id, final int maxGenerations) {
        return String.format("verification id %s, max generations %d", id, maxGenerations);
    }

}
