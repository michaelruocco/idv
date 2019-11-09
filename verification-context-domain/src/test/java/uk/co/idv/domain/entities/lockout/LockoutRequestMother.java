package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutRequest.DefaultLockoutRequestBuilder;
import uk.co.idv.domain.usecases.lockout.LockoutRequest;

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
