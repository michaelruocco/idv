package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaOnlinePurchasePolicyTest {

    private final RsaOnlinePurchasePolicy policy = new RsaOnlinePurchasePolicy();

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(UUID.fromString("8072b821-9945-49c3-b299-819ca085f2a4"));
    }

    @Test
    void shouldReturnPolicyLevel() {
        assertThat(policy.getLevel()).isInstanceOf(RsaOnlinePurchasePolicyLevel.class);
    }

    @Test
    void shouldReturnPushNotificationMethod() {
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();

        final VerificationSequencePolicy sequencePolicy = IterableUtils.get(sequencePolicies, 0);

        assertThat(sequencePolicy.getMethodPolicies()).containsExactly(new RsaPushNotificationPolicy());
    }

    @Test
    void shouldReturnMobilePinsentryMethod() {
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();

        final VerificationSequencePolicy sequencePolicy = IterableUtils.get(sequencePolicies, 1);

        assertThat(sequencePolicy.getMethodPolicies()).containsExactly(new RsaMobilePinsentryPolicy());
    }

    @Test
    void shouldReturnOneTimePasscodeMethod() {
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();

        final VerificationSequencePolicy sequencePolicy = IterableUtils.get(sequencePolicies, 2);

        assertThat(sequencePolicy.getMethodPolicies()).containsExactly(new RsaOneTimePasscodePolicy());
    }

    @Test
    void shouldReturnPhysicalPinsentryMethod() {
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();

        final VerificationSequencePolicy sequencePolicy = IterableUtils.get(sequencePolicies, 3);

        assertThat(sequencePolicy.getMethodPolicies()).containsExactly(new RsaPhysicalPinsentryPolicy());
    }

}
