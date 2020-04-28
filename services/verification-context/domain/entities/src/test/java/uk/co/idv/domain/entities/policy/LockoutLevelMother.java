package uk.co.idv.domain.entities.policy;

import uk.co.idv.domain.entities.policy.DefaultPolicyLevel.DefaultPolicyLevelBuilder;

import java.util.Collections;

public class LockoutLevelMother {

    public static PolicyLevel aliasLockoutLevel() {
        return commonBuilder()
                .aliasTypes(Collections.singleton("fake-alias-type"))
                .build();
    }

    public static PolicyLevel defaultLockoutLevel() {
        return commonBuilder()
                .aliasTypes(Collections.singleton(PolicyLevel.ALL))
                .build();
    }

    private static DefaultPolicyLevelBuilder commonBuilder() {
        return DefaultPolicyLevel.builder()
                .channelId("fake-channel")
                .activityNames(Collections.singleton("fake-activity"));
    }

}
