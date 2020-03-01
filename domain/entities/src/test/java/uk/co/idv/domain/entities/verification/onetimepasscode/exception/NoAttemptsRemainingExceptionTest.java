package uk.co.idv.domain.entities.verification.onetimepasscode.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NoAttemptsRemainingExceptionTest {

    @Test
    void shouldPopulateMessage() {
        final UUID id = UUID.randomUUID();
        final int maxAttempts = 3;
        final Throwable error = new NoAttemptsRemainingException(id, maxAttempts);

        final String message = error.getMessage();

        final String expectedMessage = String.format("verification id %s, max attempts %d", id, maxAttempts);
        assertThat(message).isEqualTo(expectedMessage);
    }

}
