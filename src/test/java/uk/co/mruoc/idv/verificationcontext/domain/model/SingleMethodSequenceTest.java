package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
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
        final VerificationResult result = new FakeVerificationResultSuccessful("other-name");

        final VerificationSequence updatedSequence = sequence.addResultIfContainsMethod(result);

        assertThat(updatedSequence).isEqualTo(sequence);
    }

    @Test
    void shouldAddResultIfResultMethodMatchesSequenceMethod() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        final VerificationSequence updatedSequence = sequence.addResultIfContainsMethod(result);

        assertThat(updatedSequence).isEqualToIgnoringGivenFields(sequence, "results");
        assertThat(updatedSequence.getResults()).containsExactly(result);
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
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        final VerificationSequence sequence = new SingleMethodSequence(method, result);

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotContainSuccessfulResult() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfContainsSuccessfulResult() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        final VerificationSequence sequence = new SingleMethodSequence(method, result);

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnMethodInCollection() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getMethods()).containsExactly(method);
    }

    @Test
    void shouldReturnHasResultsFalseIfHasNoResults() {
        final VerificationMethod method = new FakeVerificationMethod();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.hasResults()).isFalse();
    }

    @Test
    void shouldReturnHasResultsTrueIfHasResults() {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        final VerificationSequence sequence = new SingleMethodSequence(method, result);

        assertThat(sequence.hasResults()).isTrue();
    }

}
