package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;

@Builder
@Slf4j
public class DefaultLockoutService implements LockoutService {

    private final LockoutAttemptRecorder attemptRecorder;
    private final LockoutStateLoader stateLoader;
    private final LockoutStateValidator stateValidator;
    private final LockoutStateResetter stateResetter;

    @Override
    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        return attemptRecorder.recordAttempt(request);
    }

    @Override
    public LockoutState loadState(final LockoutStateRequest request) {
        return stateLoader.load(request);
    }

    @Override
    public LockoutState resetState(final LockoutStateRequest request) {
        stateResetter.reset(request);
        return stateLoader.load(request);
    }

    @Override
    public void validateState(final LockoutStateRequest request) {
        stateValidator.validateState(request);
    }

}
