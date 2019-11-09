package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasesMother;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationAttemptFailed extends VerificationAttemptFailed {

    private static final UUID CONTEXT_ID = UUID.fromString("fb059cfd-5613-49fe-8f34-2264b5da8343");
    private static final UUID VERIFICATION_ID = UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01");
    private static final Instant TIMESTAMP = Instant.parse("2019-09-27T09:35:15.612Z");

    public FakeVerificationAttemptFailed() {
        this(UUID.randomUUID());
    }

    public FakeVerificationAttemptFailed(final UUID idvId) {
        this(idvId, AliasesMother.creditCardNumber());
    }

    public FakeVerificationAttemptFailed(final UUID idvId, final Alias alias) {
        super(CONTEXT_ID,
                "fake-channel",
                "fake-activity",
                alias,
                idvId,
                "fake-method",
                VERIFICATION_ID,
                TIMESTAMP);
    }

}
