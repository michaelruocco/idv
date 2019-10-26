package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationContext extends VerificationContext {

    public FakeVerificationContext() {
        super(UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8"),
                new FakeChannel(),
                AliasesMother.creditCardNumber(),
                new Identity(AliasesMother.aliases()),
                new FakeActivity(Instant.parse("2019-09-21T20:40:29.061224Z")),
                Instant.parse("2019-09-21T20:43:32.233721Z"),
                Instant.parse("2019-09-21T20:48:32.233721Z"),
                new VerificationSequences(new SingleMethodSequence(new FakeVerificationMethodEligible())));
    }

}
