package uk.co.idv.domain.entities.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts.CannotAddAttemptException;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationAttemptsTest {

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(id, idvId);

        assertThat(attempts.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdvId() {
        final UUID id = UUID.randomUUID();
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(id, idvId);

        assertThat(attempts.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnSize() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(idvId);

        assertThat(attempts.size()).isEqualTo(0);
    }

    @Test
    void shouldThrowExceptionIfIdvIdOnAttemptDoesNotMatch() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(idvId);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final UUID attemptIdvId = UUID.randomUUID();
        given(attempt.getIdvIdValue()).willReturn(attemptIdvId);

        final Throwable error = catchThrowable(() -> attempts.add(attempt));

        assertThat(error)
                .isInstanceOf(CannotAddAttemptException.class)
                .hasMessage(String.format("attempt idv id %s does not match attempts idv id %s", attemptIdvId, idvId));
    }

    @Test
    void shouldAddAttemptIfIdvIdMatches() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(idvId);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getIdvIdValue()).willReturn(idvId);

        final VerificationAttempts updatedAttempts = attempts.add(attempt);

        assertThat(updatedAttempts).containsExactly(attempt);
    }

    @Test
    void shouldReturnAttemptsAsCollection() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Arrays.asList(attempt1, attempt2));

        assertThat(attempts.collection()).containsExactly(attempt1, attempt2);
    }

    @Test
    void shouldReturnAttemptsAsStream() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Arrays.asList(attempt1, attempt2));

        assertThat(attempts.stream()).containsExactly(attempt1, attempt2);
    }

    @Test
    void shouldPrintDetails() {
        final UUID id = UUID.fromString("2a1d0080-df0d-42f7-a48e-5586ade9fbe4");
        final UUID idvId = UUID.fromString("cc0da921-c677-4ff7-8e4b-c7f72c8b944e");

        final VerificationAttempts attempts = new VerificationAttempts(id, idvId);

        assertThat(attempts.toString()).isEqualTo("VerificationAttempts(" +
                "id=2a1d0080-df0d-42f7-a48e-5586ade9fbe4, " +
                "idvId=cc0da921-c677-4ff7-8e4b-c7f72c8b944e, " +
                "attempts=[])");
    }

}
