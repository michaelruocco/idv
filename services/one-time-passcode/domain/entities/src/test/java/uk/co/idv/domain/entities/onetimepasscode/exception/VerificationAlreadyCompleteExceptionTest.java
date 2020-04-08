package uk.co.idv.domain.entities.onetimepasscode.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationAlreadyCompleteExceptionTest {

    @Test
    void shouldPopulateMessage() {
        final UUID id = UUID.randomUUID();
        final Throwable error = new VerificationAlreadyCompleteException(id);

        final String message = error.getMessage();

        final String expectedMessage = String.format("verification %s is already complete", id);
        assertThat(message).isEqualTo(expectedMessage);
    }

}
