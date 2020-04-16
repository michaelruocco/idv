package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;

import java.time.Instant;
import java.util.UUID;

public class VerificationContextMother {

    private VerificationContextMother() {
        // utility class
    }

    public static VerificationContext fake() {
        return withNextEligibleMethod(new PushNotificationEligible());
    }

    public static VerificationContext withActivity(final Activity activity) {
        return new VerificationContext(
                id(),
                ChannelMother.fake(),
                AliasesMother.creditCardNumber(),
                IdentityMother.emptyData(),
                activity,
                created(),
                expiry(),
                new VerificationSequences(new SingleMethodSequence(new PushNotificationEligible())));
    }

    public static VerificationContext withNextEligibleMethod(final VerificationMethod method) {
        return new VerificationContext(
                id(),
                ChannelMother.fake(),
                AliasesMother.creditCardNumber(),
                IdentityMother.emptyData(),
                ActivityMother.fake(),
                created(),
                expiry(),
                new VerificationSequences(new SingleMethodSequence(method)));
    }

    private static UUID id() {
        return UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8");
    }

    private static Instant created() {
        return Instant.parse("2019-09-21T20:43:32.233721Z");
    }

    private static Instant expiry() {
        return Instant.parse("2019-09-21T20:48:32.233721Z");
    }

}
