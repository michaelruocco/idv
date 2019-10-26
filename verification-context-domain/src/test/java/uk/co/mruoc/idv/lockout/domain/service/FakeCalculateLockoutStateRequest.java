package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

public class FakeCalculateLockoutStateRequest extends CalculateLockoutStateRequest {

    public FakeCalculateLockoutStateRequest() {
        this(new FakeVerificationAttempts());
    }

    public FakeCalculateLockoutStateRequest(final VerificationAttempts attempts) {
        super("fake-channel",
                "fake-activity",
                Instant.parse("2019-09-21T20:43:32.233721Z"),
                AliasesMother.creditCardNumber(),
                UUID.fromString("d23d923a-521a-422d-9ba9-8cb1c756dbee"),
                attempts);
    }

}
