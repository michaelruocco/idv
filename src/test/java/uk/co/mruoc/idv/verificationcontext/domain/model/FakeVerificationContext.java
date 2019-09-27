package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.lockout.domain.model.FakeMaxAttemptsLockoutState;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationContext extends VerificationContext {

    private static final UUID IDV_ID_VALUE = UUID.fromString("d23d923a-521a-422d-9ba9-8cb1c756dbee");

    public FakeVerificationContext() {
        super(UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8"),
                new FakeChannel(),
                new FakeCreditCardNumber(),
                new FakeIdentity(IDV_ID_VALUE),
                new FakeActivity(Instant.parse("2019-09-21T20:40:29.061224Z")),
                Instant.parse("2019-09-21T20:43:32.233721Z"),
                Instant.parse("2019-09-21T20:48:32.233721Z"),
                new VerificationSequences(new SingleMethodSequence(new FakeVerificationMethodEligible())),
                new FakeMaxAttemptsLockoutState(IDV_ID_VALUE));
    }

}
