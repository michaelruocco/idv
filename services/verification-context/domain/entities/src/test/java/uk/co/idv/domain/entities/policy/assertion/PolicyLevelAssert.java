package uk.co.idv.domain.entities.policy.assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uk.co.idv.domain.entities.policy.PolicyLevel;

public class PolicyLevelAssert extends AbstractAssert<PolicyLevelAssert, PolicyLevel> {

    public PolicyLevelAssert(final PolicyLevel actual) {
        super(actual, PolicyLevelAssert.class);
    }

    public static PolicyLevelAssert assertThat(PolicyLevel actual) {
        return new PolicyLevelAssert(actual);
    }

    public PolicyLevelAssert isEqualTo(PolicyLevel expected) {
        Assertions.assertThat(actual.getChannelId()).isEqualTo(expected.getChannelId());
        Assertions.assertThat(actual.getActivityNames()).containsExactlyElementsOf(expected.getActivityNames());
        Assertions.assertThat(actual.getAliasTypes()).containsExactlyElementsOf(expected.getAliasTypes());
        return this;
    }

}
