package uk.co.idv.domain.entities.onetimepasscode.exception;

import java.util.UUID;

public class VerificationAlreadyCompleteException extends RuntimeException {

    public VerificationAlreadyCompleteException(final UUID id) {
        this(toMessage(id));
    }

    public VerificationAlreadyCompleteException(final String message) {
        super(message);
    }

    private static String toMessage(final UUID id) {
        return String.format("verification %s is already complete", id);
    }

}
