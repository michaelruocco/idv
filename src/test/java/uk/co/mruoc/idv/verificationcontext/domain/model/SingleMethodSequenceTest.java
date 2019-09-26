package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

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
        final MobilePinsentryEligible method = mock(MobilePinsentryEligible.class);
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
        final CardCredentialsEligible method = mock(CardCredentialsEligible.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getCardCredentials()).contains(method);
    }

    @Test
    void shouldReturnEmptyIfMethodIsNotPresent() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getPhysicalPinsentry()).isEmpty();
        assertThat(sequence.getMobilePinsentry()).isEmpty();
        assertThat(sequence.getPushNotification()).isEmpty();
        assertThat(sequence.getOneTimePasscodeSms()).isEmpty();
        assertThat(sequence.getCardCredentials()).isEmpty();
    }

    @Test
    void shouldReturnDurationFromMethod() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getDuration()).isEqualTo(method.getDuration());
    }

    @Test
    void shouldReturnContainsMethod() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.containsMethod(method.getName())).isTrue();
        assertThat(sequence.containsMethod("other-name")).isFalse();
    }

    @Test
    void shouldReturnIsEligibleFromMethod() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isEligible()).isEqualTo(method.isEligible());
    }

    @Test
    void shouldReturnExistingSequenceIfResultMethodIsNotNextMethodInSequence() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = new FakeVerificationResultSuccessful("other-name");

        final VerificationSequence updatedSequence = sequence.addResultIfHasNextMethod(result);

        assertThat(updatedSequence).isSameAs(sequence);
    }

    @Test
    void shouldAddResultIfResultMethodIsNextMethodInSequence() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());

        final VerificationSequence updatedSequence = sequence.addResultIfHasNextMethod(result);

        assertThat(updatedSequence).isEqualToIgnoringGivenFields(sequence, "method");
        final VerificationMethod updatedMethod = updatedSequence.getMethod(method.getName());
        assertThat(updatedMethod).isEqualToIgnoringGivenFields(method, "results");
        assertThat(updatedMethod.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotBeCompleteIfResultsAreEmpty() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfMethodIsComplete() {
        final VerificationMethod method = mock(VerificationMethod.class);
        given(method.isComplete()).willReturn(true);

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotContainSuccessfulResult() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfMethodIsSuccessful() {
        final VerificationMethod method = mock(VerificationMethod.class);
        given(method.isSuccessful()).willReturn(true);

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnMethodInCollection() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getMethods()).containsExactly(method);
    }

    @Test
    void shouldReturnMethodName() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        final String sequenceName = sequence.getName();

        assertThat(sequenceName).isEqualTo(method.getName());
    }

    @Test
    void shouldReturnHasNextMethod() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.hasNextMethod(method.getName())).isTrue();
        assertThat(sequence.hasNextMethod("other-name")).isFalse();
    }

}
