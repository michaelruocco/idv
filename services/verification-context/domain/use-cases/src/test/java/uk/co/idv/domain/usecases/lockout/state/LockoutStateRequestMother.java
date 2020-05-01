package uk.co.idv.domain.usecases.lockout.state;

import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;

import java.time.Instant;
import java.util.UUID;

public class LockoutStateRequestMother {

    public static LockoutStateRequest build() {
        return DefaultLoadLockoutStateRequest.builder()
                .alias(AliasesMother.creditCardNumber())
                .timestamp(Instant.parse("2019-09-21T20:44:34.233444Z"))
                .activityName("fake-activity")
                .channelId("fake-channel")
                .idvIdValue(UUID.randomUUID())
                .build();
    }

}
