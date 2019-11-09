package uk.co.idv.domain.entities.verificationcontext.result;


import java.time.Instant;
import java.util.UUID;

public class FakeVerificationResultFailed extends VerificationResultFailed {

    public FakeVerificationResultFailed(final String name) {
        super(name,
                UUID.fromString("2e90499c-bd83-425c-963a-df3920cec30e"),
                Instant.parse("2019-09-25T08:39:07.797998Z")
        );
    }

}
