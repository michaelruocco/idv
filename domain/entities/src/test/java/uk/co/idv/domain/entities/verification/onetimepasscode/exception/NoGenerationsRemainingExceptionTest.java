package uk.co.idv.domain.entities.verification.onetimepasscode.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NoGenerationsRemainingExceptionTest {

    @Test
    void shouldPopulateMessage() {
        final UUID id = UUID.randomUUID();
        final int maxGenerations = 3;
        final Throwable error = new NoGenerationsRemainingException(id, maxGenerations);

        final String message = error.getMessage();

        final String expectedMessage = String.format("verification id %s, max generations %d", id, maxGenerations);
        assertThat(message).isEqualTo(expectedMessage);
    }

}
