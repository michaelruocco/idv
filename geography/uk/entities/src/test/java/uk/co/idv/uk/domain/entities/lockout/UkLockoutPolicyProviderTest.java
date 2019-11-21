package uk.co.idv.uk.domain.entities.lockout;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UkLockoutPolicyProviderTest {

    private final LockoutPolicyProvider provider = new UkLockoutPolicyProvider();

    @Test
    void shouldReturnOnePolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        assertThat(policies).hasSize(1);
    }

    @Test
    void shouldReturnRsaLockoutPolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy).isInstanceOf(RsaLockoutPolicy.class);
    }

    @Test
    void shouldReturnRsaLockoutPolicyWithId() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy.getId()).isEqualTo(UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b"));
    }

}
