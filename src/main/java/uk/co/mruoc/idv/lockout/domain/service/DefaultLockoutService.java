package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutState;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

@Builder
@Slf4j
public class DefaultLockoutService implements LockoutService {

    private final VerificationResultConverter resultConverter;
    private final VerificationAttemptsDao dao;

    @Override
    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        final VerificationResult result = request.getResult();
        final VerificationContext context = request.getContext();
        final VerificationAttempt attempt = resultConverter.toAttempt(result, context);
        final VerificationAttempts attempts = recordAttempt(attempt);
        return toLockoutState(attempts);
    }

    @Override
    public LockoutState loadState(final LoadLockoutStateRequest request) {
        final UUID idvId = request.getIdvId();
        final VerificationAttempts attempts = loadAttempts(idvId);
        return toLockoutState(attempts);
    }

    private VerificationAttempts recordAttempt(final VerificationAttempt attempt) {
        if (attempt.isSuccessful()) {
            return handleSuccessful(attempt);
        }
        return handleFailed(attempt);
    }

    private VerificationAttempts handleSuccessful(final VerificationAttempt attempt) {
        log.info("handling successful attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvId());
        final VerificationAttempts resetAttempts = attempts.reset(); //TODO add lockout level service here and then reset accordingly
        dao.save(resetAttempts);
        log.info("returning reset attempts {}", resetAttempts);
        return resetAttempts;
    }

    private VerificationAttempts handleFailed(final VerificationAttempt attempt) {
        log.info("handling failed attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvId());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
        log.info("returning updated attempts {}", updatedAttempts);
        return updatedAttempts;
    }

    private VerificationAttempts loadAttempts(final UUID idvId) {
        return dao.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private static VerificationAttempts buildEmptyAttempts(final UUID idvId) {
        return new VerificationAttempts(idvId);
    }

    private static LockoutState toLockoutState(final VerificationAttempts attempts) {
        return DefaultLockoutState.builder()
                .attempts(attempts)
                .build();
    }

}
