package uk.co.mruoc.idv.lockout.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVerificationAttemptsDaoTest {

    private final VerificationAttemptsDao dao = new InMemoryVerificationAttemptsDao();

    @Test
    void shouldReturnEmptyOptionalIfAttemptsNotFound() {
        final UUID idvId = UUID.randomUUID();

        assertThat(dao.load(idvId)).isEmpty();
    }

    @Test
    void shouldLoadSavedAttempts() {
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID());
        dao.save(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.load(attempts.getIdvId());

        assertThat(loadedAttempts).contains(attempts);
    }

}
