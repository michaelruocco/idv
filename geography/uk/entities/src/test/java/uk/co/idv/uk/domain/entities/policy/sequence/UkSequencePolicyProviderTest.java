package uk.co.idv.uk.domain.entities.policy.sequence;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.sequence.rsa.RsaOnlinePurchasePolicy;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UkSequencePolicyProviderTest {

    private final VerificationPolicyProvider provider = new UkVerificationPolicyProvider();

    @Test
    void shouldReturnPolicies() {
        final Collection<VerificationPolicy> policies = provider.getPolicies();

        assertThat(policies).hasSize(1);
    }

    @Test
    void shouldReturnRsaLockoutPolicy() {
        final Collection<VerificationPolicy> policies = provider.getPolicies();

        final VerificationPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy).isInstanceOf(RsaOnlinePurchasePolicy.class);
    }

}
