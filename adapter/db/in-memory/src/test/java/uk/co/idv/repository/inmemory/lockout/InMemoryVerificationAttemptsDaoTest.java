package uk.co.idv.repository.inmemory.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

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
