package uk.co.idv.uk.domain.entities.policy.sequence;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.sequence.rsa.RsaOnlinePurchasePolicy;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UkSequencePolicyProviderTest {

    private final VerificationSequencesPolicyProvider provider = new UkSequencesPolicyProvider();

    @Test
    void shouldReturnPolicies() {
        final Collection<VerificationSequencesPolicy> policies = provider.getPolicies();

        assertThat(policies).hasSize(1);
    }

    @Test
    void shouldReturnRsaLockoutPolicy() {
        final Collection<VerificationSequencesPolicy> policies = provider.getPolicies();

        final VerificationSequencesPolicy policy = IterableUtils.get(policies, 0);

        assertThat(policy).isInstanceOf(RsaOnlinePurchasePolicy.class);
    }

}
