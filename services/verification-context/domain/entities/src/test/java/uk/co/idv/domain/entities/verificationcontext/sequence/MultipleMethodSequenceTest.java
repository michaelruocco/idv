package uk.co.idv.domain.entities.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MultipleMethodSequenceTest {

    private static final String METHOD_NAME_1 = "fake-method-1";
    private static final String METHOD_NAME_2 = "fake-method-2";

    @Test
    void shouldReturnPhysicalPinsentry() {
        final PhysicalPinsentry method = mock(PhysicalPinsentry.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getPhysicalPinsentry()).contains(method);
    }

    @Test
    void shouldReturnMobilePinsentry() {
        final MobilePinsentry method = mock(MobilePinsentry.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getMobilePinsentry()).contains(method);
    }

    @Test
    void shouldReturnPushNotification() {
        final PushNotification method = mock(PushNotification.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getPushNotification()).contains(method);
    }

    @Test
    void shouldReturnOneTimePasscodeSms() {
        final OneTimePasscode method = mock(OneTimePasscode.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getOneTimePasscode()).contains(method);
    }

    @Test
    void shouldReturnEmptyIfMethodIsNotPresent() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getPhysicalPinsentry()).isEmpty();
        assertThat(sequence.getMobilePinsentry()).isEmpty();
        assertThat(sequence.getPushNotification()).isEmpty();
        assertThat(sequence.getOneTimePasscode()).isEmpty();
    }

    @Test
    void shouldReturnDurationFromMethodWithLongestDuration() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(Duration.ofMinutes(2));
        final VerificationMethod method2 = new FakeVerificationMethodEligible(Duration.ofMinutes(1));
        final VerificationMethod method3 = new FakeVerificationMethodEligible(Duration.ofMinutes(3));

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2, method3));

        assertThat(sequence.getDuration()).isEqualTo(method3.getDuration());
    }

    @Test
    void shouldReturnContainsMethod() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.containsMethod(method1.getName())).isTrue();
        assertThat(sequence.containsMethod(method2.getName())).isTrue();
        assertThat(sequence.containsMethod("other-name")).isFalse();
    }

    @Test
    void shouldReturnContainsMethodComplete() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(new FakeVerificationResultSuccessful(METHOD_NAME_1));
        final VerificationMethod method2 = new FakeVerificationMethodEligible(new FakeVerificationResultFailed(METHOD_NAME_2));

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.containsCompleteMethod(method1.getName())).isTrue();
        assertThat(sequence.containsCompleteMethod(method2.getName())).isFalse();
    }

    @Test
    void shouldReturnIsEligibleIfAllMethodsAreEligible() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible();
        final VerificationMethod method2 = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldNotAddResultToMethodIfResultMethodIsNotNextMethodInSequence() {
        final VerificationMethod method = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));
        final VerificationResult result = new FakeVerificationResultSuccessful("other-name");

        sequence.addResultIfHasNextMethod(result);

        assertThat(method.getResults()).isEmpty();
    }

    @Test
    void shouldNotAddResultToMethodIfResultMethodIsNextMethodInSequenceButNotEligible() {
        final VerificationMethod method = new FakeVerificationMethodIneligible(METHOD_NAME_1);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        sequence.addResultIfHasNextMethod(result);

        assertThat(method.getResults()).isEmpty();
    }

    @Test
    void shouldAddResultToMethodIfResultMethodIsNextMethodInSequence() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);
        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));
        final VerificationResult result = new FakeVerificationResultSuccessful(method1.getName());

        sequence.addResultIfHasNextMethod(result);

        assertThat(method1.getResults()).containsExactly(result);
        assertThat(method2.getResults()).isEmpty();
    }

    @Test
    void shouldNotBeCompleteIfResultsAreEmpty() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible();
        final VerificationMethod method2 = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeCompleteIfAllMethodsDoNotHaveResult() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfAllMethodsAreComplete() {
        final VerificationMethod method1 = mock(VerificationMethod.class);
        given(method1.isComplete()).willReturn(true);
        final VerificationMethod method2 = mock(VerificationMethod.class);
        given(method2.isComplete()).willReturn(true);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfResultsAreEmpty() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldNotBeSuccessfulIfAllMethodsDoNotHaveSuccessfulResult() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfAllMethodsHaveSuccessfulResult() {
        final VerificationMethod method1 = mock(VerificationMethod.class);
        given(method1.isSuccessful()).willReturn(true);
        final VerificationMethod method2 = mock(VerificationMethod.class);
        given(method2.isSuccessful()).willReturn(true);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnMethods() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.getMethods()).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnDefaultNameIfNotSpecified() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible("method1");
        final VerificationMethod method2 = new FakeVerificationMethodEligible("method2");

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        final String expectedName = String.format("%s_%s", method1.getName(), method2.getName());
        assertThat(sequence.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldReturnSpecifiedName() {
        final String name = "my-specific-name";
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new MultipleMethodSequence(name, Collections.singleton(method));

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnHasNextMethod() {
        final VerificationMethod method1 = new FakeVerificationMethodEligible(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.hasNextMethod(method1.getName())).isTrue();
        assertThat(sequence.hasNextMethod(method2.getName())).isFalse();
    }

}
