package uk.co.idv.domain.entities.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence.MethodNotFoundInSequenceException;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
        final OneTimePasscode method = mock(OneTimePasscode.class);
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getOneTimePasscode()).contains(method);
    }

    @Test
    void shouldReturnEmptyIfMethodIsNotPresent() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getPhysicalPinsentry()).isEmpty();
        assertThat(sequence.getMobilePinsentry()).isEmpty();
        assertThat(sequence.getPushNotification()).isEmpty();
        assertThat(sequence.getOneTimePasscode()).isEmpty();
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
    void shouldReturnContainsCompleteTrueIfMethodIsComplete() {
        final VerificationMethod completeMethod = new FakeVerificationMethodEligible(new FakeVerificationResultSuccessful(FakeVerificationMethod.NAME));

        final VerificationSequence sequence = new SingleMethodSequence(completeMethod);

        assertThat(sequence.containsCompleteMethod(completeMethod.getName())).isTrue();
    }

    @Test
    void shouldReturnContainsCompleteMethodFalseIfMethodIsIncomplete() {
        final VerificationMethod incompleteMethod = new FakeVerificationMethodEligible(new FakeVerificationResultFailed(FakeVerificationMethod.NAME));

        final VerificationSequence sequence = new SingleMethodSequence(incompleteMethod);

        assertThat(sequence.containsCompleteMethod(incompleteMethod.getName())).isFalse();
    }

    @Test
    void shouldReturnIsEligibleFromMethod() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.isEligible()).isEqualTo(method.isEligible());
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

    @Test
    void shouldReturnHasNextMethodFalseIfNextMethodIsComplete() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        method.addResult(new FakeVerificationResultSuccessful(method.getName()));

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.hasNextMethod(method.getName())).isFalse();
    }

    @Test
    void shouldReturnMethodByName() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final VerificationSequence sequence = new SingleMethodSequence(method);

        assertThat(sequence.getMethod(method.getName())).isEqualTo(method);
    }

    @Test
    void shouldNotAddResultToMethodIfMethodNameDoesNotMatch() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        sequence.addResultIfHasNextMethod(VerificationResultsMother.successful("other-method"));

        assertThat(method.getResults()).isEmpty();
    }

    @Test
    void shouldNotResultToMethodIfMethodNameMatches() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationResult result = VerificationResultsMother.successful(method.getName());

        sequence.addResultIfHasNextMethod(result);

        assertThat(method.getResults()).containsExactly(result);
    }

    @Test
    void shouldThrowExceptionIfMethodWithNameCannotBeFound() {
        final VerificationMethod method = new FakeVerificationMethodEligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);

        final Throwable cause = catchThrowable(() -> sequence.getMethod("other-method"));

        assertThat(cause)
                .isInstanceOf(MethodNotFoundInSequenceException.class)
                .hasMessage("cannot find method other-method in sequence fake-method sequence");
    }

}
