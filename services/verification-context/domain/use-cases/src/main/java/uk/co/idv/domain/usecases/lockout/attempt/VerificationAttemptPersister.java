package uk.co.idv.domain.usecases.lockout.attempt;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

@Builder
@Slf4j
public class VerificationAttemptPersister {

    private final VerificationAttemptsLoader attemptsLoader;
    private final VerificationAttemptDao dao;

    public void persist(final VerificationAttempt attempt) {
        final VerificationAttempts attempts = attemptsLoader.load(attempt.getIdvIdValue());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
    }

}
