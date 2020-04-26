package uk.co.idv.domain.entities.onetimepasscode.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NoDeliveriesRemainingExceptionTest {

    @Test
    void shouldPopulateMessage() {
        final UUID id = UUID.randomUUID();
        final int maxDeliveries = 3;
        final Throwable error = new NoDeliveriesRemainingException(id, maxDeliveries);

        final String message = error.getMessage();

        final String expectedMessage = String.format("verification id %s, max deliveries %d", id, maxDeliveries);
        assertThat(message).isEqualTo(expectedMessage);
    }

}
