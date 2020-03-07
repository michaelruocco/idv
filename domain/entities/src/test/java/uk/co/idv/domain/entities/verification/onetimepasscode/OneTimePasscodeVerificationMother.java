package uk.co.idv.domain.entities.verification.onetimepasscode;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class OneTimePasscodeVerificationMother {

    private OneTimePasscodeVerificationMother() {
        // utility class
    }

    public static OneTimePasscodeVerification pending() {
        final Instant created = Instant.parse("2019-09-21T20:44:32.233721Z");
        return OneTimePasscodeVerification.builder()
                .id(UUID.fromString("3cf1ae88-5e5b-46ec-b641-1b35adf17dd2"))
                .created(created)
                .expiry(created.plus(Duration.ofMinutes(5)))
                .maxAttempts(5)
                .maxDeliveryAttempts(3)
                .build();

    }

}
