package uk.co.idv.domain.entities.lockout.assertion;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

public class LockoutAssertions {

    public static LockoutLevelAssert assertThat(LockoutLevel actual) {
        return new LockoutLevelAssert(actual);
    }

}
