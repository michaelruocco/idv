package uk.co.idv.api.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyAttributesConverterTest {

    private final LockoutPolicy policy1 = mock(LockoutPolicy.class);
    private final LockoutPolicy policy2 = mock(LockoutPolicy.class);

    private final LockoutPolicyAttributes attributes1 = mock(LockoutPolicyAttributes.class);
    private final LockoutPolicyAttributes attributes2 = mock(LockoutPolicyAttributes.class);

    private final LockoutPolicyAttributesConverterDelegator delegator = mock(LockoutPolicyAttributesConverterDelegator.class);

    private final DefaultLockoutPolicyAttributesConverter converter = new DefaultLockoutPolicyAttributesConverter(delegator);

    @Test
    void shouldConvertMultipleAttributes() {
        given(delegator.toAttributes(policy1)).willReturn(attributes1);
        given(delegator.toAttributes(policy2)).willReturn(attributes2);

        Collection<LockoutPolicyAttributes> attributes = converter.toAttributes(Arrays.asList(policy1, policy2));

        assertThat(attributes).containsExactly(attributes1, attributes2);
    }


    @Test
    void shouldConvertMultiplePolicies() {
        given(delegator.toPolicy(attributes1)).willReturn(policy1);
        given(delegator.toPolicy(attributes2)).willReturn(policy2);

        Collection<LockoutPolicy> policies = converter.toPolicies(Arrays.asList(attributes1, attributes2));

        assertThat(policies).containsExactly(policy1, policy2);
    }

}