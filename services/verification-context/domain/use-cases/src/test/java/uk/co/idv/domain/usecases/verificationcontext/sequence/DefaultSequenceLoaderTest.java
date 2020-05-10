package uk.co.idv.domain.usecases.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultSequenceLoaderTest {

    private final VerificationPolicyService policyService = mock(VerificationPolicyService.class);

    private final SequenceLoader loader = new DefaultSequenceLoader(policyService);

    @Test
    void shouldLoadSequencesBasedOnPolicy() {
        final LoadSequencesRequest request = mock(LoadSequencesRequest.class);
        final VerificationPolicy policy = mock(VerificationPolicy.class);
        given(policyService.load(request)).willReturn(policy);
        final VerificationSequences expectedSequences = mock(VerificationSequences.class);
        given(policy.buildSequences(request)).willReturn(expectedSequences);

        final VerificationSequences sequences = loader.loadSequences(request);

        assertThat(sequences).isEqualTo(expectedSequences);
    }

}
