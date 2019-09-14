package uk.co.mruoc.verificationcontext.domain.result;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultFailed extends VerificationResultFailed {

    public FakeVerificationResultFailed(final String name) {
        super(name, UUID.randomUUID(), Instant.now());
    }

}
