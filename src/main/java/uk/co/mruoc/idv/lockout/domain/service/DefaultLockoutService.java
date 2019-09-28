package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

@Builder
@Slf4j
public class DefaultLockoutService implements LockoutService {

    private final LockoutAttemptRecorder attemptRecorder;
    private final LockoutStateLoader stateLoader;
    private final LockoutStateValidator stateValidator;

    @Override
    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        return attemptRecorder.recordAttempt(request);
    }

    @Override
    public LockoutState loadState(final LoadLockoutStateRequest request) {
        return stateLoader.load(request);
    }

    @Override
    public void validateState(final LoadLockoutStateRequest request) {
        stateValidator.validateState(request);
    }

}
