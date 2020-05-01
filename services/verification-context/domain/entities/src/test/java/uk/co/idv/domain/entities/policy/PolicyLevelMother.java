package uk.co.idv.domain.entities.policy;

import uk.co.idv.domain.entities.policy.DefaultPolicyLevel.DefaultPolicyLevelBuilder;

import java.util.Collections;

public class PolicyLevelMother {

    public static PolicyLevel aliasPolicyLevel() {
        return commonBuilder()
                .aliasTypes(Collections.singleton("fake-alias-type"))
                .build();
    }

    public static PolicyLevel defaultPolicyLevel() {
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
