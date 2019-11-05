package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest.DefaultLockoutRequestBuilder;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

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
