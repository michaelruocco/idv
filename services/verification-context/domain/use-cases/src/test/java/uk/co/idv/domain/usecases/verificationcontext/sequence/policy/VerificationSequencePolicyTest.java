package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method.VerificationMethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationSequencePolicyTest {

    @Test
    void shouldCreateSequenceWithSingleMethod() {
        final VerificationMethodPolicy methodParameters = mock(VerificationMethodPolicy.class);
        final VerificationSequencePolicy sequenceParameters = new VerificationSequencePolicy(methodParameters);
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        final VerificationMethod expectedMethod = OneTimePasscodeMother.eligible();
        given(methodParameters.buildMethod(request)).willReturn(expectedMethod);

        final VerificationSequence sequence = sequenceParameters.buildSequence(request);

        assertThat(sequence.getName()).isEqualTo(expectedMethod.getName());
        assertThat(sequence.getMethods()).containsExactly(expectedMethod);
    }

    @Test
    void shouldCreateSequenceWithMultipleMethods() {
        final String sequenceName = "sequence-name";
        final VerificationMethodPolicy methodParameters1 = mock(VerificationMethodPolicy.class);
        final VerificationMethodPolicy methodParameters2 = mock(VerificationMethodPolicy.class);
        final VerificationSequencePolicy sequenceParameters = new VerificationSequencePolicy(
                sequenceName,
                methodParameters1,
                methodParameters2
        );
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        final VerificationMethod expectedMethod1 = OneTimePasscodeMother.eligible();
        given(methodParameters1.buildMethod(request)).willReturn(expectedMethod1);
        final VerificationMethod expectedMethod2 = PushNotificationMother.eligible();
        given(methodParameters2.buildMethod(request)).willReturn(expectedMethod2);

        final VerificationSequence sequence = sequenceParameters.buildSequence(request);

        assertThat(sequence.getName()).isEqualTo(sequenceName);
        assertThat(sequence.getMethods()).containsExactly(expectedMethod1, expectedMethod2);
    }

}
