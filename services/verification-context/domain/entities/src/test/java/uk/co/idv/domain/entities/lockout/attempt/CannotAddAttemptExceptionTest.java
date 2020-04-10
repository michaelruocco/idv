package uk.co.idv.domain.entities.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts.CannotAddAttemptException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CannotAddAttemptExceptionTest {

    private static final UUID IDV_ID = UUID.fromString("f29f4ebb-e15a-4bc7-a89c-e2caea35280b");
    private static final UUID ATTEMPT_IDV_ID = UUID.fromString("315430f9-f4ff-40a1-9db3-0f7ff95432c8");

    @Test
    void shouldReturnMessage() {
        final Throwable error = new CannotAddAttemptException(IDV_ID, ATTEMPT_IDV_ID);

        final String expectedMessage = String.format("attempt idv id %s does not match attempts idv id %s", ATTEMPT_IDV_ID, IDV_ID);
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnIdvId() {
        final CannotAddAttemptException error = new CannotAddAttemptException(IDV_ID, ATTEMPT_IDV_ID);

        assertThat(error.getIdvId()).isEqualTo(IDV_ID);
    }

    @Test
    void shouldReturnAttemptIdvId() {
        final CannotAddAttemptException error = new CannotAddAttemptException(IDV_ID, ATTEMPT_IDV_ID);

        assertThat(error.getAttemptIdvId()).isEqualTo(ATTEMPT_IDV_ID);
    }

}
