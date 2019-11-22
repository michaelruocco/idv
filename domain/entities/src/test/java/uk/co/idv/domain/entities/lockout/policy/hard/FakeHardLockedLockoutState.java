package uk.co.idv.domain.entities.lockout.policy.hard;


import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;

import java.util.UUID;

public class FakeHardLockedLockoutState extends HardLockoutState {

    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");
    private static final int MAX_NUMBER_OF_ATTEMPTS = 1;

    public FakeHardLockedLockoutState() {
        this(IDV_ID);
    }

    public FakeHardLockedLockoutState(final UUID idvId) {
        this(idvId, MAX_NUMBER_OF_ATTEMPTS);
    }

    public FakeHardLockedLockoutState(final UUID idvId, int maxNumberOfAttempts) {
        super(VerificationAttemptsMother.oneAttempt(idvId), maxNumberOfAttempts);
    }

}
