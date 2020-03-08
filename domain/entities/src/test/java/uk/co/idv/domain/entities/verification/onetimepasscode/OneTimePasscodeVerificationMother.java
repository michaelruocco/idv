package uk.co.idv.domain.entities.verification.onetimepasscode;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

public class OneTimePasscodeVerificationMother {

    private OneTimePasscodeVerificationMother() {
        // utility class
    }

    public static OneTimePasscodeVerification pending() {
        final Instant created = Instant.parse("2019-09-21T20:44:32.233721Z");
        return OneTimePasscodeVerification.builder()
                .id(UUID.fromString("3cf1ae88-5e5b-46ec-b641-1b35adf17dd2"))
                .contextId(UUID.fromString("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8"))
                .created(created)
                .expiry(created.plus(Duration.ofMinutes(5)))
                .maxDeliveryAttempts(3)
                .maxAttempts(5)
                .deliveries(Collections.singleton(OneTimePasscodeDeliveryMother.smsDelivery()))
                .attempts(Collections.singleton(OneTimePasscodeVerificationAttemptMother.attempt()))
                .build();

    }

}
