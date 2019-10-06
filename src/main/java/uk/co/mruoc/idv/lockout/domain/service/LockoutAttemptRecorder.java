package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

@Builder
@Slf4j
public class LockoutAttemptRecorder {

    private final VerificationResultConverter resultConverter;
    private final VerificationAttemptsLoader attemptsLoader;
    private final VerificationAttemptsDao dao;
    private final LockoutPolicyService policyService;

    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        final VerificationResult result = request.getResult();
        final VerificationContext context = request.getContext();
        final VerificationAttempt attempt = resultConverter.toAttempt(result, context);
        if (policyService.shouldRecordAttempt(request)) {
            return recordAttempt(attempt);
        }
        return loadState(attempt);
    }

    private LockoutState recordAttempt(final VerificationAttempt attempt) {
        if (attempt.isSuccessful()) {
            return resetLockoutState(attempt);
        }
        return saveFailedAttempt(attempt);
    }

    private LockoutState resetLockoutState(final VerificationAttempt attempt) {
        log.info("resetting lockout state after successful attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvIdValue());
        return policyService.resetState(attempt.withAttempts(attempts));
    }

    private LockoutState saveFailedAttempt(final VerificationAttempt attempt) {
        log.info("saving failed attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvIdValue());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
        return calculateState(attempt, updatedAttempts);
    }

    private LockoutState loadState(final VerificationAttempt attempt) {
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvIdValue());
        return calculateState(attempt, attempts);
    }

    private VerificationAttempts loadAttempts(final UUID idvId) {
        return attemptsLoader.load(idvId);
    }

    private LockoutState calculateState(final VerificationAttempt attempt,
                                               final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = attempt.withAttempts(attempts);
        return policyService.calculateState(request);
    }

}
