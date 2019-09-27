package uk.co.mruoc.idv.lockout.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.VerificationAttempts;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.Collections;
import java.util.UUID;

@Builder
@Slf4j
public class DefaultVerificationAttemptsService implements VerificationAttemptsService {

    private final VerificationResultConverter resultConverter;
    private final VerificationAttemptsDao dao;

    @Override
    public VerificationAttempts recordAttempt(final VerificationResult result, final VerificationContext context) {
        final VerificationAttempt attempt = resultConverter.toAttempt(result, context);
        return recordAttempt(attempt);
    }

    private VerificationAttempts recordAttempt(final VerificationAttempt attempt) {
        if (attempt.isSuccessful()) {
            return handleSuccessful(attempt);
        }
        return handleFailed(attempt);
    }

    private VerificationAttempts handleSuccessful(final VerificationAttempt attempt) {
        log.info("handling successful attempt {}", attempt);
        final VerificationAttempts attempts = load(attempt.getIdvId());
        final VerificationAttempts resetAttempts = attempts.reset(); //TODO add lockout level service here and then reset accordingly
        dao.save(resetAttempts);
        log.info("returning reset attempts {}", resetAttempts);
        return resetAttempts;
    }

    private VerificationAttempts handleFailed(final VerificationAttempt attempt) {
        log.info("handling failed attempt {}", attempt);
        final VerificationAttempts attempts = load(attempt.getIdvId());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
        log.info("returning updated attempts {}", updatedAttempts);
        return updatedAttempts;
    }

    @Override
    public VerificationAttempts load(final UUID idvId) {
        return dao.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private static VerificationAttempts buildEmptyAttempts(final UUID idvId) {
        return VerificationAttempts.builder()
                .idvId(idvId)
                .attempts(Collections.emptyList())
                .build();
    }

}
