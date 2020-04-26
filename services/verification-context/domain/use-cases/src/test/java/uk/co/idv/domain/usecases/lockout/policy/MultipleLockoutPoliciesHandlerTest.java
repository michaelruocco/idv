package uk.co.idv.domain.usecases.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleLockoutPoliciesHandlerTest {

    private final MultipleLockoutPoliciesHandler handler = new MultipleLockoutPoliciesHandler();

    @Test
    void shouldReturnEmptyOptionalIfNoPoliciesPassed() {
        final List<LockoutPolicy> policies = Collections.emptyList();

        final Optional<LockoutPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldReturnPolicyIfOnePolicyPassed() {
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.hardLockoutPolicy();
        final List<LockoutPolicy> policies = Collections.singletonList(expectedPolicy);

        final Optional<LockoutPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnFirstAliasLevelPolicyIfMultiplePoliciesPassed() {
        final LockoutPolicy defaultLevelPolicy = LockoutPolicyMother.hardLockoutPolicy(LockoutLevelMother.defaultLockoutLevel());
        final LockoutPolicy aliasLevelPolicy = LockoutPolicyMother.hardLockoutPolicy(LockoutLevelMother.aliasLockoutLevel());
        final List<LockoutPolicy> policies = Arrays.asList(defaultLevelPolicy, aliasLevelPolicy);

        final Optional<LockoutPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(aliasLevelPolicy);
    }

    @Test
    void shouldReturnPolicyIfMultipleDefaultLevelPoliciesArePassed() {
        final LockoutPolicy policy1 = LockoutPolicyMother.hardLockoutPolicy(LockoutLevelMother.defaultLockoutLevel());
        final LockoutPolicy policy2 = LockoutPolicyMother.hardLockoutPolicy(LockoutLevelMother.defaultLockoutLevel());
        final List<LockoutPolicy> policies = Arrays.asList(policy1, policy2);

        final Optional<LockoutPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(policy1);
    }

}
