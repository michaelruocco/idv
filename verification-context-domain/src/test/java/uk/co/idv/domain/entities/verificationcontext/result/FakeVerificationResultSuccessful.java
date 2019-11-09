package uk.co.idv.domain.entities.verificationcontext.result;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultSuccessful extends VerificationResultSuccessful {

    public FakeVerificationResultSuccessful(final String methodName) {
        super(methodName,
                UUID.fromString("524d0e6d-cead-49f3-b0f5-04e29e789681"),
                Instant.parse("2019-09-24T15:52:49.569287Z")
        );
    }

}
