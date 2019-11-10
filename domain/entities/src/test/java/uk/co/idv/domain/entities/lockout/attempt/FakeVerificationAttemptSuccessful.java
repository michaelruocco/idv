package uk.co.idv.domain.entities.lockout.attempt;

import uk.co.idv.domain.entities.identity.alias.AliasesMother;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationAttemptSuccessful extends VerificationAttemptSuccessful {

    private static final UUID CONTEXT_ID = UUID.fromString("fb059cfd-5613-49fe-8f34-2264b5da8343");
    private static final UUID VERIFICATION_ID = UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01");
    private static final Instant TIMESTAMP = Instant.parse("2019-09-27T09:35:15.612Z");

    public FakeVerificationAttemptSuccessful() {
        this(UUID.randomUUID());
    }

    public FakeVerificationAttemptSuccessful(final UUID idvId) {
        super(CONTEXT_ID,
                "fake-channel",
                "fake-activity",
                AliasesMother.creditCardNumber(),
                idvId,
                "fake-method",
                VERIFICATION_ID,
                TIMESTAMP);
    }

}
