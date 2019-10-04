package uk.co.mruoc.idv.lockout.domain.model;

import java.util.UUID;

public class FakeLockoutStateMaxAttempts extends LockoutStateMaxAttempts {

    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public FakeLockoutStateMaxAttempts() {
        this(IDV_ID);
    }

    public FakeLockoutStateMaxAttempts(final UUID idvId) {
        this(idvId, MAX_NUMBER_OF_ATTEMPTS);
    }

    public FakeLockoutStateMaxAttempts(int maxNumberOfAttempts) {
        this(IDV_ID, maxNumberOfAttempts);
    }

    public FakeLockoutStateMaxAttempts(final UUID idvId, int maxNumberOfAttempts) {
        super(new FakeVerificationAttempts(idvId), maxNumberOfAttempts);
    }

}
