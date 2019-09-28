package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.UUID;

@Builder
public class LockoutStateLoader {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutStateCalculator stateCalculator;

    public LockoutState load(final LoadLockoutStateRequest request) {
        final VerificationAttempts attempts = attemptsLoader.load(request.getIdvIdValue());
        return calculateLockoutState(request, attempts);
    }

    private LockoutState calculateLockoutState(final LoadLockoutStateRequest loadStateRequest, final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = LoadStateCalculateLockoutStateRequest.builder()
                .loadStateRequest(loadStateRequest)
                .attempts(attempts)
                .build();
        return stateCalculator.calculate(request);
    }

}
