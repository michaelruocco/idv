package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method.MethodPolicy;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePolicyTest {

    @Test
    void shouldCreateSequenceWithSingleMethod() {
        final MethodPolicy methodParameters = mock(MethodPolicy.class);
        final SequencePolicy sequenceParameters = new SequencePolicy(methodParameters);
        final LoadSequencesRequest request = LoadSequencesRequestMother.build();
        final VerificationMethod expectedMethod = OneTimePasscodeMother.eligible();
        given(methodParameters.buildMethod(request)).willReturn(expectedMethod);

        final VerificationSequence sequence = sequenceParameters.buildSequence(request);

        assertThat(sequence.getName()).isEqualTo(expectedMethod.getName());
        assertThat(sequence.getMethods()).containsExactly(expectedMethod);
    }

    @Test
    void shouldCreateSequenceWithMultipleMethods() {
        final String sequenceName = "sequence-name";
        final MethodPolicy methodParameters1 = mock(MethodPolicy.class);
        final MethodPolicy methodParameters2 = mock(MethodPolicy.class);
        final SequencePolicy sequenceParameters = new SequencePolicy(
                sequenceName,
                methodParameters1,
                methodParameters2
        );
        final LoadSequencesRequest request = LoadSequencesRequestMother.build();
        final VerificationMethod expectedMethod1 = OneTimePasscodeMother.eligible();
        given(methodParameters1.buildMethod(request)).willReturn(expectedMethod1);
        final VerificationMethod expectedMethod2 = PushNotificationMother.eligible();
        given(methodParameters2.buildMethod(request)).willReturn(expectedMethod2);

        final VerificationSequence sequence = sequenceParameters.buildSequence(request);

        assertThat(sequence.getName()).isEqualTo(sequenceName);
        assertThat(sequence.getMethods()).containsExactly(expectedMethod1, expectedMethod2);
    }

}
