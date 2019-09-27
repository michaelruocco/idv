package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.MaxAttemptsLockoutState;

import java.util.UUID;

public class FakeMaxAttemptsLockoutState extends MaxAttemptsLockoutState {

    private static final UUID IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public FakeMaxAttemptsLockoutState() {
        this(IDV_ID);
    }

    public FakeMaxAttemptsLockoutState(final UUID idvId) {
        this(idvId, MAX_NUMBER_OF_ATTEMPTS);
    }

    public FakeMaxAttemptsLockoutState(int maxNumberOfAttempts) {
        this(IDV_ID, maxNumberOfAttempts);
    }

    public FakeMaxAttemptsLockoutState(final UUID idvId, int maxNumberOfAttempts) {
        super(new FakeVerificationAttempts(idvId), maxNumberOfAttempts);
    }

}
