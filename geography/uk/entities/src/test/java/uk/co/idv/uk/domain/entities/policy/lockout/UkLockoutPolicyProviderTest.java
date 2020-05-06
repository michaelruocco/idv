package uk.co.idv.uk.domain.entities.policy.lockout;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.lockout.as3.As3LockoutPolicy;
import uk.co.idv.uk.domain.entities.policy.lockout.rsa.RsaLockoutPolicy;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UkLockoutPolicyProviderTest {

    private final LockoutPolicyProvider provider = new UkLockoutPolicyProvider();

    @Test
    void shouldReturnPolicies() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        assertThat(policies).hasSize(2);
    }

    @Test
    void shouldReturnRsaLockoutPolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy).isInstanceOf(RsaLockoutPolicy.class);
    }

    @Test
    void shouldReturnAs3LockoutPolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 1);

        assertThat(policy).isInstanceOf(As3LockoutPolicy.class);
    }

}
