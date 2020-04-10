package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyItemsConverterTest {

    private final LockoutPolicyItemConverter itemConverter = mock(LockoutPolicyItemConverter.class);

    private final LockoutPolicyItemsConverter converter = new LockoutPolicyItemsConverter(itemConverter);

    @Test
    void shouldConvertMultipleItemsToPolicies() {
        final Item item1 = new Item().with("id", "1");
        final Item item2 = new Item().with("id", "2");
        final LockoutPolicy policy1 = LockoutPolicyMother.nonLockingPolicy();
        final LockoutPolicy policy2 = LockoutPolicyMother.hardLockoutPolicy();
        given(itemConverter.toPolicy(item1)).willReturn(policy1);
        given(itemConverter.toPolicy(item2)).willReturn(policy2);

        final Collection<LockoutPolicy> policies = converter.toPolicies(Arrays.asList(item1, item2));

        assertThat(policies).containsExactly(policy1, policy2);
    }

    @Test
    void shouldConvertItemToPolicy() {
        final Item item = new Item();
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.nonLockingPolicy();
        given(itemConverter.toPolicy(item)).willReturn(expectedPolicy);

        final LockoutPolicy policy = converter.toPolicy(item);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldConvertPolicyToItem() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();
        final Item expectedItem = new Item();
        given(itemConverter.toItem(policy)).willReturn(expectedItem);

        final Item item = converter.toItem(policy);

        assertThat(item).isEqualTo(expectedItem);
    }

}
