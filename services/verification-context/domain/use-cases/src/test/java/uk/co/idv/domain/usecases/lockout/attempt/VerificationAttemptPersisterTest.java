package uk.co.idv.domain.usecases.lockout.attempt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationAttemptPersisterTest {

    private final FakeVerificationAttemptsLoader attemptsLoader = new FakeVerificationAttemptsLoader();
    private final FakeVerificationAttemptDao dao = new FakeVerificationAttemptDao();
    private final VerificationAttempts existingAttempts = VerificationAttemptsMother.oneAttempt();

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
        final VerificationAttempt attempt = VerificationAttemptsMother.failed(existingAttempts.getIdvId());

        persister.persist(attempt);

        assertThat(attemptsLoader.getLastLoadedIdvIdValue()).isEqualTo(attempt.getIdvIdValue());
    }

    @Test
    void shouldAddAttemptToExistingAttemptsBeforePersisting() {
        final VerificationAttempt attempt = VerificationAttemptsMother.failed(existingAttempts.getIdvId());

        persister.persist(attempt);

        final VerificationAttempts updatedAttempts = dao.getLastSavedAttempts();
        assertThat(updatedAttempts).containsExactlyElementsOf(existingAttempts.add(attempt));
    }

}
