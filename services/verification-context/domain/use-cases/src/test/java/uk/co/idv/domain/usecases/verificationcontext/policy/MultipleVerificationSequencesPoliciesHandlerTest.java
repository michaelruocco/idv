package uk.co.idv.domain.usecases.verificationcontext.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicyMother;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MultipleVerificationSequencesPoliciesHandlerTest {

    private final MultipleVerificationSequencesPoliciesHandler handler = new MultipleVerificationSequencesPoliciesHandler();

    @Test
    void shouldReturnEmptyOptionalIfNoPoliciesPassed() {
        final List<VerificationSequencesPolicy> policies = Collections.emptyList();

        final Optional<VerificationSequencesPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldReturnPolicyIfOnePolicyPassed() {
        final VerificationSequencesPolicy expectedPolicy = VerificationSequencesPolicyMother.build();
        final List<VerificationSequencesPolicy> policies = Collections.singletonList(expectedPolicy);

        final Optional<VerificationSequencesPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnEmptyOptionalIfMultiplePoliciesPassed() {
        final List<VerificationSequencesPolicy> policies = Arrays.asList(
                mock(VerificationSequencesPolicy.class),
                mock(VerificationSequencesPolicy.class)
        );

        final Optional<VerificationSequencesPolicy> policy = handler.extractPolicy(policies);

        assertThat(policy).isEmpty();
    }

}
