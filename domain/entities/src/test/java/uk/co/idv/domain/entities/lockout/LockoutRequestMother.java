package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest.DefaultLockoutRequestBuilder;

public class LockoutRequestMother {

    public static LockoutRequest fake() {
        return fakeBuilder().build();
    }

    public static DefaultLockoutRequestBuilder fakeBuilder() {
        return DefaultLockoutRequest.builder()
                .channelId("fake-channel")
                .activityName("fake-activity")
                .alias(AliasesMother.creditCardNumber());
    }

}
