package uk.co.idv.domain.entities.lockout.policy;

public class LockoutLevelMother {

    public static AliasLockoutLevel aliasLockoutLevel() {
        return AliasLockoutLevel.builder()
                .channelId("fake-channel")
                .activityName("fake-activity")
                .aliasType("fake-alias-type")
                .build();
    }

    public static LockoutLevel defaultLockoutLevel() {
        return DefaultLockoutLevel.builder()
                .channelId("fake-channel")
                .activityName("fake-activity")
                .build();
    }

}
