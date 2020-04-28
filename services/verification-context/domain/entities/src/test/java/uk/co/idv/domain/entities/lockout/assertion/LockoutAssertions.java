package uk.co.idv.domain.entities.lockout.assertion;

import uk.co.idv.domain.entities.policy.PolicyLevel;

public class LockoutAssertions {

    private LockoutAssertions() {
        // utility class
    }

    public static LockoutLevelAssert assertThat(PolicyLevel actual) {
        return new LockoutLevelAssert(actual);
    }

}
