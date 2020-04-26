package uk.co.idv.domain.entities.onetimepasscode.exception;

import java.util.UUID;

public class NoDeliveriesRemainingException extends RuntimeException {

    public NoDeliveriesRemainingException(final UUID id, final int maxDeliveries) {
        this(toMessage(id, maxDeliveries));
    }

    public NoDeliveriesRemainingException(final String message) {
        super(message);
    }

    private static String toMessage(final UUID id, final int maxDeliveries) {
        return String.format("verification id %s, max deliveries %d", id, maxDeliveries);
    }

}
