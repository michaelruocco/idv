package uk.co.mruoc.idv.lockout.domain.model;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class FakeVerificationAttempts extends VerificationAttempts {

    private static final UUID ID = UUID.fromString("20a32973-2018-46e8-a93c-28892e71da64");
    private static final UUID DEFAULT_IDV_ID = UUID.fromString("49888d4b-36ac-4d1c-b529-3829869e5858");

    public FakeVerificationAttempts() {
        this(DEFAULT_IDV_ID);
    }

    public FakeVerificationAttempts(final UUID idvId) {
        super(ID, idvId, buildAttempts(idvId));
    }

    private static Collection<VerificationAttempt> buildAttempts(final UUID idvId) {
        return Collections.singleton(new FakeVerificationAttemptSuccessful(idvId));
    }

}
