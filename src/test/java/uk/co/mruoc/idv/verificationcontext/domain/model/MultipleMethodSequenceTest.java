package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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
        final OneTimePasscodeSms method = mock(OneTimePasscodeSms.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getOneTimePasscodeSms()).contains(method);
    }

    @Test
    void shouldReturnCardCredentials() {
        final CardCredentials method = mock(CardCredentials.class);
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getCardCredentials()).contains(method);
    }

    @Test
    void shouldReturnEmptyIfMethodIsNotPresent() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.getPhysicalPinsentry()).isEmpty();
        assertThat(sequence.getMobilePinsentry()).isEmpty();
        assertThat(sequence.getPushNotification()).isEmpty();
        assertThat(sequence.getOneTimePasscodeSms()).isEmpty();
        assertThat(sequence.getCardCredentials()).isEmpty();
    }

    @Test
    void shouldReturnDurationFromMethodWithLongestDuration() {
        final VerificationMethod method1 = new FakeVerificationMethod(Duration.ofMinutes(10));
        final VerificationMethod method2 = new FakeVerificationMethod(Duration.ofMinutes(3));

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.getDuration()).isEqualTo(method1.getDuration());
    }

    @Test
    void shouldReturnContainsMethod() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.containsMethod(method1.getName())).isTrue();
        assertThat(sequence.containsMethod(method2.getName())).isTrue();
        assertThat(sequence.containsMethod("other-name")).isFalse();
    }

    @Test
    void shouldReturnIsEligibleIfAllMethodsAreEligible() {
        final VerificationMethod method1 = new FakeVerificationMethod();
        final VerificationMethod method2 = new FakeVerificationMethod();

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldNotAddResultIfResultMethodDoesNotMatchSequenceMethods() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));
        final VerificationResult result = new FakeVerificationResultSuccessful("other-name");

        final VerificationSequence updatedSequence = sequence.addResultIfContainsMethod(result);

        assertThat(updatedSequence).isEqualTo(sequence);
    }

    @Test
    void shouldAddResultIfResultMethodMatchesSequenceMethod() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));
        final VerificationResult result = new FakeVerificationResultSuccessful(method1.getName());

        final VerificationSequence updatedSequence = sequence.addResultIfContainsMethod(result);

        assertThat(updatedSequence).isEqualToIgnoringGivenFields(sequence, "results");
        assertThat(updatedSequence.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotBeCompleteIfResultsAreEmpty() {
        final VerificationMethod method1 = new FakeVerificationMethod();
        final VerificationMethod method2 = new FakeVerificationMethod();

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2));

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeCompleteIfAllMethodsDoNotHaveResult() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationResult result = new FakeVerificationResultSuccessful(method1.getName());

        final VerificationSequence sequence = new MultipleMethodSequence(Arrays.asList(method1, method2), result);

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfAllMethodsHaveResult() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationResult result1 = new FakeVerificationResultSuccessful(method1.getName());
        final VerificationResult result2 = new FakeVerificationResultSuccessful(method2.getName());

        final VerificationSequence sequence = new MultipleMethodSequence(
                Arrays.asList(method1, method2),
                Arrays.asList(result1, result2)
        );

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfResultsAreEmpty() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new MultipleMethodSequence(Collections.singleton(method));

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldNotBeSuccessfulIfAllMethodsDoNotHaveSuccessfulResult() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationResult result1 = new FakeVerificationResultSuccessful(method1.getName());
        final VerificationResult result2 = new FakeVerificationResultFailed(method2.getName());

        final VerificationSequence sequence = new MultipleMethodSequence(
                Arrays.asList(method1, method2),
                Arrays.asList(result1, result2)
        );

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfAllMethodsHaveSuccessfulResult() {
        final VerificationMethod method1 = new FakeVerificationMethod(METHOD_NAME_1);
        final VerificationMethod method2 = new FakeVerificationMethod(METHOD_NAME_2);
        final VerificationResult result1 = new FakeVerificationResultSuccessful(method1.getName());
        final VerificationResult result2 = new FakeVerificationResultSuccessful(method2.getName());

        final VerificationSequence sequence = new MultipleMethodSequence(
                Arrays.asList(method1, method2),
                Arrays.asList(result1, result2)
        );

        assertThat(sequence.isSuccessful()).isTrue();
    }

}