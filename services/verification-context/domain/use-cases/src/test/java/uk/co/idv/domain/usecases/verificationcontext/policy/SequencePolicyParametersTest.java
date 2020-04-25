package uk.co.idv.domain.usecases.verificationcontext.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;
import uk.co.idv.domain.usecases.verificationcontext.method.MethodPolicyParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePolicyParametersTest {

    @Test
    void shouldCreateSequenceWithSingleMethod() {
        final MethodPolicyParameters methodParameters = mock(MethodPolicyParameters.class);
        final SequencePolicyParameters sequenceParameters = new SequencePolicyParameters(methodParameters);
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
        final MethodPolicyParameters methodParameters1 = mock(MethodPolicyParameters.class);
        final MethodPolicyParameters methodParameters2 = mock(MethodPolicyParameters.class);
        final SequencePolicyParameters sequenceParameters = new SequencePolicyParameters(
                sequenceName,
                methodParameters1,
                methodParameters2
        );
        final LoadSequencesRequest request = LoadSequencesRequestMother.build();
        final VerificationMethod expectedMethod1 = OneTimePasscodeMother.eligible();
        given(methodParameters1.buildMethod(request)).willReturn(expectedMethod1);
        final VerificationMethod expectedMethod2 = new PushNotificationEligible();
        given(methodParameters2.buildMethod(request)).willReturn(expectedMethod2);

        final VerificationSequence sequence = sequenceParameters.buildSequence(request);

        assertThat(sequence.getName()).isEqualTo(sequenceName);
        assertThat(sequence.getMethods()).containsExactly(expectedMethod1, expectedMethod2);
    }

}
