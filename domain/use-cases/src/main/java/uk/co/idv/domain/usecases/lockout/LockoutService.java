package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;

public interface LockoutService {

    LockoutState recordAttempt(final RecordAttemptRequest request);

    LockoutState loadState(final LockoutStateRequest request);

    LockoutState resetState(final LockoutStateRequest request);

    void validateState(final LockoutStateRequest request);

}
