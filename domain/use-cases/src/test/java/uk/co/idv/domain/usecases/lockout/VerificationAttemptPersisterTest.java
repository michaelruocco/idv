package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.FakeVerificationAttemptFailed;
import uk.co.idv.domain.entities.lockout.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationAttemptPersisterTest {

    private final FakeVerificationAttemptsLoader attemptsLoader = new FakeVerificationAttemptsLoader();
    private final FakeVerificationAttemptsDao dao = new FakeVerificationAttemptsDao();
    private final VerificationAttempts existingAttempts = new FakeVerificationAttempts();

    private final VerificationAttemptPersister persister = VerificationAttemptPersister.builder()
            .attemptsLoader(attemptsLoader)
            .dao(dao)
            .build();

    @BeforeEach
    void setUp() {
        attemptsLoader.setAttemptsToLoad(existingAttempts);
    }

    @Test
    void shouldPassIdvIdWhenLoadingAttempts() {
        final VerificationAttempt attempt = new FakeVerificationAttemptFailed(existingAttempts.getIdvId());

        persister.persist(attempt);

        assertThat(attemptsLoader.getLastLoadedIdvIdValue()).isEqualTo(attempt.getIdvIdValue());
    }

    @Test
    void shouldAddAttemptToExistingAttemptsBeforePersisting() {
        final VerificationAttempt attempt = new FakeVerificationAttemptFailed(existingAttempts.getIdvId());

        persister.persist(attempt);

        final VerificationAttempts updatedAttempts = dao.getLastSavedAttempts();
        assertThat(updatedAttempts).containsExactlyElementsOf(existingAttempts.add(attempt));
    }

}
