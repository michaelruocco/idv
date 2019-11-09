package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.entities.lockout.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.RecordAttemptRequest;

public interface LockoutService {

    LockoutState recordAttempt(final RecordAttemptRequest request);

    LockoutState loadState(final LockoutStateRequest request);

    LockoutState resetState(final LockoutStateRequest request);

    void validateState(final LockoutStateRequest request);

}
