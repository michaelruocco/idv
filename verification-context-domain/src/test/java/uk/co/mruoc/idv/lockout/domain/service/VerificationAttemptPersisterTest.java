package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.dao.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
