package uk.co.idv.domain.entities.policy.assertion;

import uk.co.idv.domain.entities.policy.PolicyLevel;

public class PolicyAssertions {

    private PolicyAssertions() {
        // utility class
    }

    public static PolicyLevelAssert assertThat(PolicyLevel actual) {
        return new PolicyLevelAssert(actual);
    }

}
