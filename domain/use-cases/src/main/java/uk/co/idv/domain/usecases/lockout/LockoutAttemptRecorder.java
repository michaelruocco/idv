package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;

@Builder
@Slf4j
public class LockoutAttemptRecorder {

    private final RecordAttemptRequestConverter requestConverter;
    private final LockoutPolicyService policyService;
    private final LockoutStateLoader stateLoader;
    private final LockoutStateResetter stateResetter;
    private final VerificationAttemptPersister attemptPersister;

    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        final VerificationAttempt attempt = requestConverter.toAttempt(request);
        if (policyService.shouldRecordAttempt(request)) {
            return recordAttempt(attempt);
        }
        return loadState(attempt);
    }

    private LockoutState recordAttempt(final VerificationAttempt attempt) {
        if (attempt.isSuccessful()) {
            return resetLockoutState(attempt);
        }
        return recordFailedAttempt(attempt);
    }

    private LockoutState resetLockoutState(final VerificationAttempt attempt) {
        stateResetter.reset(attempt);
        return stateLoader.load(attempt);
    }

    private LockoutState recordFailedAttempt(final VerificationAttempt attempt) {
        attemptPersister.persist(attempt);
        return stateLoader.load(attempt);
    }

    private LockoutState loadState(final VerificationAttempt attempt) {
        return stateLoader.load(attempt);
    }

}
