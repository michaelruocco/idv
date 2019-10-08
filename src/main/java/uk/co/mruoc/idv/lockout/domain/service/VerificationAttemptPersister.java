package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

@Builder
@Slf4j
public class VerificationAttemptPersister {

    private final VerificationAttemptsLoader attemptsLoader;
    private final VerificationAttemptsDao dao;

    public void persist(final VerificationAttempt attempt) {
        final VerificationAttempts attempts = attemptsLoader.load(attempt.getIdvIdValue());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
    }

}
