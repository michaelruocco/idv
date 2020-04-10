package uk.co.idv.domain.entities.lockout.assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

public class LockoutLevelAssert extends AbstractAssert<LockoutLevelAssert, LockoutLevel> {

    public LockoutLevelAssert(final LockoutLevel actual) {
        super(actual, LockoutLevelAssert.class);
    }

    public static LockoutLevelAssert assertThat(LockoutLevel actual) {
        return new LockoutLevelAssert(actual);
    }

    public LockoutLevelAssert isEqualTo(LockoutLevel expected) {
        Assertions.assertThat(actual.getChannelId()).isEqualTo(expected.getChannelId());
        Assertions.assertThat(actual.getActivityNames()).containsExactlyElementsOf(expected.getActivityNames());
        Assertions.assertThat(actual.getAliasTypes()).containsExactlyElementsOf(expected.getAliasTypes());
        return this;
    }

}
