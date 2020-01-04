package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;

import java.time.Instant;
import java.util.UUID;

public class FakeVerificationContext extends VerificationContext {

    public FakeVerificationContext() {
        super(UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8"),
                ChannelMother.fake(),
                AliasesMother.creditCardNumber(),
                new Identity(AliasesMother.aliases()),
                ActivityMother.fake(),
                Instant.parse("2019-09-21T20:43:32.233721Z"),
                Instant.parse("2019-09-21T20:48:32.233721Z"),
                new VerificationSequences(new SingleMethodSequence(new PushNotificationEligible())));
    }

}
