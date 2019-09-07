package uk.co.mruoc.verificationcontext.domain.result;

import uk.co.mruoc.verificationcontext.domain.result.VerificationResultFailed;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultFailed extends VerificationResultFailed {

    public FakeVerificationResultFailed(final String name) {
        super(name, UUID.randomUUID(), Instant.now());
    }

}
