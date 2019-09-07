package uk.co.mruoc.verificationcontext.domain;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.verificationcontext.domain.method.CardCredentials;
import uk.co.mruoc.verificationcontext.domain.method.FakeVerificationMethod;
import uk.co.mruoc.verificationcontext.domain.method.MobilePinsentry;
import uk.co.mruoc.verificationcontext.domain.method.OneTimePasscodeSms;
import uk.co.mruoc.verificationcontext.domain.method.PhysicalPinsentry;
import uk.co.mruoc.verificationcontext.domain.method.PushNotification;
import uk.co.mruoc.verificationcontext.domain.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SingleMethodSequenceTest {

    @Test
    void shouldReturnPhysicalPinsentry() {
        final PhysicalPinsentry method = mock(PhysicalPinsentry.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getPhysicalPinsentry()).contains(method);
    }

    @Test
    void shouldReturnMobilePinsentry() {
        final MobilePinsentry method = mock(MobilePinsentry.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getMobilePinsentry()).contains(method);
    }

    @Test
    void shouldReturnPushNotification() {
        final PushNotification method = mock(PushNotification.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getPushNotification()).contains(method);
    }

    @Test
    void shouldReturnOneTimePasscodeSms() {
        final OneTimePasscodeSms method = mock(OneTimePasscodeSms.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getOneTimePasscodeSms()).contains(method);
    }

    @Test
    void shouldReturnCardCredentials() {
        final CardCredentials method = mock(CardCredentials.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getCardCredentials()).contains(method);
    }

    @Test
    void shouldReturnEmptyIfMethodIsNotPresent() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getPhysicalPinsentry()).isEmpty();
        assertThat(sequence.getMobilePinsentry()).isEmpty();
        assertThat(sequence.getPushNotification()).isEmpty();
        assertThat(sequence.getOneTimePasscodeSms()).isEmpty();
        assertThat(sequence.getCardCredentials()).isEmpty();
    }

    @Test
    void shouldReturnDurationFromMethod() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getDuration()).isEqualTo(method.getDuration());
    }

    @Test
    void shouldReturnContainsMethod() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.containsMethod(method.getName())).isTrue();
        assertThat(sequence.containsMethod("other-name")).isFalse();
    }

    @Test
    void shouldReturnIsEligibleFromMethod() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isEligible()).isEqualTo(method.isEligible());
    }

    @Test
    void shouldNotAddResultIfResultMethodDoesNotMatchSequenceMethod() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = buildResultWithMethodName("other-name");

        final VerificationSequence newSequence = sequence.addResultIfContainsMethod(result);

        assertThat(newSequence).isEqualTo(sequence);
    }

    @Test
    void shouldAddResultIfResultMethodMatchesSequenceMethod() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = buildResultWithMethodName(method.getName());

        final VerificationSequence newSequence = sequence.addResultIfContainsMethod(result);

        assertThat(newSequence).isEqualToIgnoringGivenFields(sequence, "results");
        assertThat(newSequence.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotBeCompleteIfResultsAreEmpty() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfResultsAreNotEmpty() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationResult result = buildResultWithMethodName(method.getName());

        final VerificationSequence sequence = new SingleMethodSequence(method, result);

        assertThat(sequence.isComplete()).isTrue();
    }

    private static VerificationResult buildResultWithMethodName(final String methodName) {
        final VerificationResult result = mock(VerificationResult.class);
        given(result.getMethodName()).willReturn(methodName);
        return result;
    }

}
