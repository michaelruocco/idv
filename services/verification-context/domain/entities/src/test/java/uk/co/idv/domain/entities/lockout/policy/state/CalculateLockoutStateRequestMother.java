package uk.co.idv.domain.entities.lockout.policy.state;

import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest.CalculateLockoutStateRequestBuilder;

import java.time.Instant;
import java.util.UUID;

public class CalculateLockoutStateRequestMother {

    private CalculateLockoutStateRequestMother() {
        // utility class
    }

    public static CalculateLockoutStateRequest withOneAttempt() {
        return defaultBuilder().build();
    }

    public static CalculateLockoutStateRequestBuilder defaultBuilder() {
        return CalculateLockoutStateRequest.builder()
                .channelId("fake-channel")
                .activityName("fake-activity")
                .timestamp(Instant.parse("2019-09-21T20:43:32.233721Z"))
                .alias(AliasesMother.creditCardNumber())
                .idvIdValue(UUID.fromString("d23d923a-521a-422d-9ba9-8cb1c756dbee"))
                .attempts(VerificationAttemptsMother.oneAttempt());
    }

}
