package uk.co.idv.domain.entities.onetimepasscode.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NoDeliveryAttemptsRemainingExceptionTest {

    @Test
    void shouldPopulateMessage() {
        final UUID id = UUID.randomUUID();
        final int maxDeliveryAttempts = 3;
        final Throwable error = new NoDeliveryAttemptsRemainingException(id, maxDeliveryAttempts);

        final String message = error.getMessage();

        final String expectedMessage = String.format("verification id %s, max delivery attempts %d", id, maxDeliveryAttempts);
        assertThat(message).isEqualTo(expectedMessage);
    }

}
