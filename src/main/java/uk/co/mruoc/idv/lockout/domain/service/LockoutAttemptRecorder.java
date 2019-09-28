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
    private final LockoutStateCalculator stateCalculator;
    private final VerificationAttemptsLoader attemptsLoader;
    private final VerificationAttemptsDao dao;

    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        final VerificationResult result = request.getResult();
        final VerificationContext context = request.getContext();
        //TODO how should we decide whether to record an attempt? e.g. only if attempt is complete
        final VerificationAttempt attempt = resultConverter.toAttempt(result, context);
        final VerificationAttempts attempts = recordAttempt(attempt);
        return calculateLockoutState(attempt, attempts);
    }

    private VerificationAttempts recordAttempt(final VerificationAttempt attempt) {
        if (attempt.isSuccessful()) {
            return handleSuccessful(attempt);
        }
        return handleFailed(attempt);
    }

    private VerificationAttempts handleSuccessful(final VerificationAttempt attempt) {
        log.info("handling successful attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvIdValue());
        final VerificationAttempts resetAttempts = attempts.reset(); //TODO add lockout level service here and then reset accordingly
        dao.save(resetAttempts);
        log.info("returning reset attempts {}", resetAttempts);
        return resetAttempts;
    }

    private VerificationAttempts handleFailed(final VerificationAttempt attempt) {
        log.info("handling failed attempt {}", attempt);
        final VerificationAttempts attempts = loadAttempts(attempt.getIdvIdValue());
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        dao.save(updatedAttempts);
        log.info("returning updated attempts {}", updatedAttempts);
        return updatedAttempts;
    }

    private VerificationAttempts loadAttempts(final UUID idvId) {
        return attemptsLoader.load(idvId);
    }

    private LockoutState calculateLockoutState(final VerificationAttempt attempt,
                                               final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .channelId(attempt.getChannelId())
                .activityName(attempt.getActivityName())
                .alias(attempt.getProvidedAlias())
                .timestamp(attempt.getTimestamp())
                .idvIdValue(attempt.getIdvIdValue())
                .attempts(attempts)
                .build();
        return stateCalculator.calculate(request);
    }

}
