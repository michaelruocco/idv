package uk.co.idv.domain.entities.lockout;


import java.util.UUID;

public class FakeLockoutStateMaxAttemptsLocked extends LockoutStateMaxAttempts {

    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");
    private static final int MAX_NUMBER_OF_ATTEMPTS = 1;

    public FakeLockoutStateMaxAttemptsLocked() {
        this(IDV_ID);
    }

    public FakeLockoutStateMaxAttemptsLocked(final UUID idvId) {
        this(idvId, MAX_NUMBER_OF_ATTEMPTS);
    }

    public FakeLockoutStateMaxAttemptsLocked(int maxNumberOfAttempts) {
        this(IDV_ID, maxNumberOfAttempts);
    }

    public FakeLockoutStateMaxAttemptsLocked(final UUID idvId, int maxNumberOfAttempts) {
        super(new FakeVerificationAttempts(idvId), maxNumberOfAttempts);
    }

}
