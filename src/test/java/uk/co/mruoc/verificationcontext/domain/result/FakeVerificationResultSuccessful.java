package uk.co.mruoc.verificationcontext.domain.result;

import uk.co.mruoc.verificationcontext.domain.result.VerificationResultSuccessful;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultSuccessful extends VerificationResultSuccessful {

    public FakeVerificationResultSuccessful(final String name) {
        super(name, UUID.randomUUID(), Instant.now());
    }

}
