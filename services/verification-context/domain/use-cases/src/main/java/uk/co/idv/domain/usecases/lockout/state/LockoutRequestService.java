package uk.co.idv.domain.usecases.lockout.state;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;

@Builder
public class LockoutRequestService {

    @Builder.Default
    private final LockoutStateRequestConverter requestConverter = new LockoutStateRequestConverter();

    private final VerificationAttemptsLoader attemptsLoader;

    public CalculateLockoutStateRequest toCalculateRequest(final LockoutStateRequest request) {
        final VerificationAttempts attempts = attemptsLoader.load(request.getIdvIdValue());
        return requestConverter.toCalculateRequest(request, attempts);
    }

}
