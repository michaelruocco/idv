package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel.DefaultLockoutLevelBuilder;

import java.util.Collections;

public class LockoutLevelMother {

    public static LockoutLevel aliasLockoutLevel() {
        return commonBuilder()
                .aliasTypes(Collections.singleton("fake-alias-type"))
                .build();
    }

    public static LockoutLevel defaultLockoutLevel() {
        return commonBuilder()
                .aliasTypes(Collections.singleton(LockoutLevel.ALL))
                .build();
    }

    private static DefaultLockoutLevelBuilder commonBuilder() {
        return DefaultLockoutLevel.builder()
                .channelId("fake-channel")
                .activityNames(Collections.singleton("fake-activity"));
    }

}
