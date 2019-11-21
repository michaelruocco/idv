package uk.co.idv.domain.entities.lockout.policy.hard;

import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;

import java.util.UUID;

public class FakeHardLockoutState extends HardLockoutState {

    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public FakeHardLockoutState() {
        this(IDV_ID);
    }

    public FakeHardLockoutState(final UUID idvId) {
        this(idvId, MAX_NUMBER_OF_ATTEMPTS);
    }

    public FakeHardLockoutState(int maxNumberOfAttempts) {
        this(IDV_ID, maxNumberOfAttempts);
    }

    public FakeHardLockoutState(final UUID idvId, int maxNumberOfAttempts) {
        super(new FakeVerificationAttempts(idvId), maxNumberOfAttempts);
    }

}
