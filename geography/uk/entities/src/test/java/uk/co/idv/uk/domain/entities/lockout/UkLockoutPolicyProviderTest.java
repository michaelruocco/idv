package uk.co.idv.uk.domain.entities.lockout;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UkLockoutPolicyProviderTest {

    private final LockoutPolicyProvider provider = new UkLockoutPolicyProvider();

    @Test
    void shouldReturnTwoPolicies() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        assertThat(policies).hasSize(2);
    }

    @Test
    void shouldReturnFirstRsaLockoutPolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy).isInstanceOf(RsaLockoutPolicy.class);
    }

    @Test
    void shouldReturnFirstRsaLockoutPolicyForCreditCardAlias() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        final AliasLockoutLevel level = (AliasLockoutLevel) policy.getLockoutLevel();
        assertThat(level.getAliasType()).isEqualTo(CreditCardNumber.TYPE);
    }

    @Test
    void shouldReturnFirstRsaLockoutPolicyWithId() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy.getId()).isEqualTo(UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b"));
    }

    @Test
    void shouldReturnSecondFirstRsaLockoutPolicy() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 1);

        assertThat(policy).isInstanceOf(RsaLockoutPolicy.class);
    }

    @Test
    void shouldReturnSecondRsaLockoutPolicyForDebitCardAlias() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 1);

        final AliasLockoutLevel level = (AliasLockoutLevel) policy.getLockoutLevel();
        assertThat(level.getAliasType()).isEqualTo(DebitCardNumber.TYPE);
    }

    @Test
    void shouldReturnSecondRsaLockoutPolicyWithId() {
        final Collection<LockoutPolicy> policies = provider.getPolicies();

        final LockoutPolicy policy = IterableUtils.get(policies, 1);

        assertThat(policy.getId()).isEqualTo(UUID.fromString("455a23f9-6505-491b-aa0f-2d4bf06acbbe"));
    }

}
