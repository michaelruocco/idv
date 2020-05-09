package uk.co.idv.domain.entities.verificationcontext.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultVerificationPolicyTest {

    private static final UUID ID = UUID.randomUUID();

    private final PolicyLevel level = mock(PolicyLevel.class);
    private final VerificationSequencePolicy sequencePolicy1 = mock(VerificationSequencePolicy.class);
    private final VerificationSequencePolicy sequencePolicy2 = mock(VerificationSequencePolicy.class);
    private final Collection<VerificationSequencePolicy> sequencePolicies = Arrays.asList(sequencePolicy1, sequencePolicy2);

    private final VerificationPolicy policy = new DefaultVerificationPolicy(ID, level, sequencePolicies);

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnAppliesToFromPolicyLevel() {
        final boolean expectedAppliesTo = true;
        final VerificationPolicyRequest request = mock(VerificationPolicyRequest.class);
        given(level.appliesTo(request)).willReturn(expectedAppliesTo);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isEqualTo(expectedAppliesTo);
    }

    @Test
    void shouldReturnSequencesFromEachSequencePolicy() {
        final VerificationPolicyRequest request = mock(VerificationPolicyRequest.class);
        final VerificationSequence expectedSequence1 = mock(VerificationSequence.class);
        given(sequencePolicy1.buildSequence(request)).willReturn(expectedSequence1);
        final VerificationSequence expectedSequence2 = mock(VerificationSequence.class);
        given(sequencePolicy2.buildSequence(request)).willReturn(expectedSequence2);

        final VerificationSequences sequences = policy.buildSequences(request);

        assertThat(sequences).containsExactly(
                expectedSequence1,
                expectedSequence2
        );
    }

}
