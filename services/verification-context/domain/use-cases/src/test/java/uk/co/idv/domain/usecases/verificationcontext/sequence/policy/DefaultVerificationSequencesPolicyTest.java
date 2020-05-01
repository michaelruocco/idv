package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultVerificationSequencesPolicyTest {

    private final PolicyLevel level = mock(PolicyLevel.class);
    private final SequencePolicy sequencePolicy1 = mock(SequencePolicy.class);
    private final SequencePolicy sequencePolicy2 = mock(SequencePolicy.class);
    private final Collection<SequencePolicy> sequencePolicies = Arrays.asList(sequencePolicy1, sequencePolicy2);

    private final VerificationSequencesPolicy policy = new DefaultVerificationSequencesPolicy(level, sequencePolicies);

    @Test
    void shouldReturnAppliesToFromPolicyLevel() {
        final boolean expectedAppliesTo = true;
        final LoadSequencesRequest request = mock(LoadSequencesRequest.class);
        given(level.appliesTo(request)).willReturn(expectedAppliesTo);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isEqualTo(expectedAppliesTo);
    }

    @Test
    void shouldReturnSequencesFromEachSequencePolicy() {
        final LoadSequencesRequest request = mock(LoadSequencesRequest.class);
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