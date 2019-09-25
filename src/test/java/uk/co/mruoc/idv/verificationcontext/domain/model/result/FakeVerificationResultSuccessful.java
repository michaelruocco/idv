package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultSuccessful extends VerificationResultSuccessful {

    public FakeVerificationResultSuccessful(final String methodName) {
        super(methodName, UUID.randomUUID(), Instant.now());
    }

}
