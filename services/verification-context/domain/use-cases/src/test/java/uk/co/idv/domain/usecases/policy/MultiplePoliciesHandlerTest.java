package uk.co.idv.domain.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.Policy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MultiplePoliciesHandlerTest {

    private final MultiplePoliciesHandler<Policy> handler = new MultiplePoliciesHandler<>();

    @Test
    void shouldReturnEmptyOptionalIfNoPoliciesPassed() {
        final List<Policy> policies = Collections.emptyList();

        final Optional<Policy> policy = handler.extractPolicy(policies);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldReturnPolicyIfOnePolicyPassed() {
        final Policy expectedPolicy = mock(Policy.class);
        final List<Policy> policies = Collections.singletonList(expectedPolicy);

        final Optional<Policy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnFirstAliasLevelPolicyIfMultiplePoliciesPassed() {
        final Policy defaultLevelPolicy = defaultLevelPolicy();
        final Policy aliasLevelPolicy = aliasLevelPolicy();
        final List<Policy> policies = Arrays.asList(defaultLevelPolicy, aliasLevelPolicy);

        final Optional<Policy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(aliasLevelPolicy);
    }

    @Test
    void shouldReturnFirstPolicyIfMultipleDefaultLevelPoliciesArePassedThatAreTheSame() {
        final Policy policy1 = defaultLevelPolicy();
        final Policy policy2 = defaultLevelPolicy();
        final List<Policy> policies = Arrays.asList(policy1, policy2);

        final Optional<Policy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(policy1);
    }

    @Test
    void shouldReturnFirstPolicyIfMultipleAliasLevelPoliciesArePassedThatAreTheSame() {
        final Policy policy1 = aliasLevelPolicy();
        final Policy policy2 = aliasLevelPolicy();
        final List<Policy> policies = Arrays.asList(policy1, policy2);

        final Optional<Policy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(policy1);
    }

    private Policy defaultLevelPolicy() {
        final Policy policy = mock(Policy.class);
        given(policy.isAliasLevel()).willReturn(false);
        return policy;
    }

    private Policy aliasLevelPolicy() {
        final Policy policy = mock(Policy.class);
        given(policy.isAliasLevel()).willReturn(true);
        return policy;
    }

}
