package uk.co.idv.domain.entities.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences.CannotDetermineWhichNextMethodToUseException;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationSequencesTest {

    @Test
    void shouldReturnEmptyTrueIfContainsNoSequences() {
        final VerificationSequences sequences = new VerificationSequences();

        assertThat(sequences.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnZeroDurationIfAttemptToCalculateMaxDurationOfEmptySequences() {
        final VerificationSequences sequences = new VerificationSequences();

        final Duration duration = sequences.calculateMaxDuration();

        assertThat(duration).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnEmptyFalseIfContainsNoSequences() {
        final VerificationSequence sequence = mock(VerificationSequence.class);

        final VerificationSequences sequences = new VerificationSequences(sequence);

        assertThat(sequences.isEmpty()).isFalse();
    }

    @Test
    void shouldBeIterable() {
        final VerificationSequence sequence1 = mock(VerificationSequence.class);
        final VerificationSequence sequence2 = mock(VerificationSequence.class);

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences).containsExactly(sequence1, sequence2);
    }

    @Test
    void shouldReturnStream() {
        final VerificationSequence sequence1 = mock(VerificationSequence.class);
        final VerificationSequence sequence2 = mock(VerificationSequence.class);

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences.stream()).containsExactly(sequence1, sequence2);
    }

    @Test
    void shouldCalculateMaxDuration() {
        final VerificationSequence sequence1 = mock(VerificationSequence.class);
        given(sequence1.getDuration()).willReturn(Duration.ofMinutes(5));

        final VerificationSequence sequence2 = mock(VerificationSequence.class);
        given(sequence2.getDuration()).willReturn(Duration.ofMinutes(10));

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences.calculateMaxDuration()).isEqualTo(Duration.ofMinutes(10));
    }

    @Test
    void shouldAddResultToMethodIfResultIsForNextMethodInSequenceAndMethodIsEligible() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final VerificationMethod method = new FakeVerificationMethodEligible(result.getMethodName());
        final VerificationSequence sequence = new SingleMethodSequence(method);

        final VerificationSequences sequences = new VerificationSequences(sequence);

        sequences.addResultIfHasSequencesWithNextMethod(result);

        assertThat(method.getResults()).containsExactly(result);
    }

    @Test
    void shouldThrowExceptionIfAddingResultWithMethodThatIsNextMethodInSequenceButNotEligible() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final VerificationMethod method = new FakeVerificationMethodIneligible(result.getMethodName());
        final VerificationSequence sequence = new SingleMethodSequence(method);

        final VerificationSequences sequences = new VerificationSequences(sequence);

        final Throwable error = catchThrowable(() -> sequences.addResultIfHasSequencesWithNextMethod(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfAddingResultWithMethodThatIsNotNextMethodInSequence() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationSequence sequence1 = new SingleMethodSequence(new FakeVerificationMethodEligible("other-name-1"));
        final VerificationSequence sequence2 = new SingleMethodSequence(new FakeVerificationMethodEligible("other-name-2"));

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        final Throwable error = catchThrowable(() -> sequences.addResultIfHasSequencesWithNextMethod(result));

        assertThat(error)
                .isInstanceOf(NotNextMethodInSequenceException.class)
                .hasMessage("method-name is not the next method in any sequences");
    }

    @Test
    void shouldReturnContainsSequenceWithCompleteMethod() {
        final VerificationMethod completeMethod = new FakeVerificationMethodEligible(new FakeVerificationResultSuccessful("method-name-1"));
        final VerificationMethod incompleteMethod = new FakeVerificationMethodEligible(new FakeVerificationResultFailed("method-name-2"));
        final VerificationSequence sequence1 = new SingleMethodSequence(completeMethod);
        final VerificationSequence sequence2 = new SingleMethodSequence(incompleteMethod);

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences.containsCompleteMethod(completeMethod.getName())).isTrue();
        assertThat(sequences.containsCompleteMethod(incompleteMethod.getName())).isFalse();
    }

    @Test
    void shouldReturnContainsCompleteSequenceContainingMethod() {
        final VerificationMethod completeMethod = new FakeVerificationMethodEligible(new FakeVerificationResultSuccessful("method-name-1"));
        final VerificationMethod incompleteMethod = new FakeVerificationMethodEligible(new FakeVerificationResultFailed("method-name-2"));
        final VerificationSequence sequence1 = new SingleMethodSequence(completeMethod);
        final VerificationSequence sequence2 = new SingleMethodSequence(incompleteMethod);

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences.containsCompleteSequenceContainingMethod(completeMethod.getName())).isTrue();
        assertThat(sequences.containsCompleteSequenceContainingMethod(incompleteMethod.getName())).isFalse();
    }

    @Test
    void shouldThrowExceptionIfMethodIsNotNextMethodInSequence() {
        final String methodName = "method-name";
        final VerificationSequences sequences = new VerificationSequences();

        final Throwable error = catchThrowable(() -> sequences.getNextEligibleMethod(methodName));

        final String expectedMessage = String.format("%s is not the next method in any sequences", methodName);
        assertThat(error)
                .isInstanceOf(NotNextMethodInSequenceException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void shouldReturnMethodIfMethodIsNextMethodInSequence() {
        final VerificationMethod oneTimePasscode = OneTimePasscodeMother.eligible();
        final VerificationSequence sequence = new SingleMethodSequence(oneTimePasscode);
        final VerificationSequences sequences = new VerificationSequences(sequence);

        final VerificationMethod method = sequences.getNextEligibleMethod(oneTimePasscode.getName());

        assertThat(method).isEqualTo(oneTimePasscode);
    }

    @Test
    void shouldReturnMethodIfMethodIsNextMethodInTwoSequencesAndMethodIsTheSame() {
        final OneTimePasscode oneTimePasscode = OneTimePasscodeMother.eligible();
        final VerificationSequence sequence1 = new SingleMethodSequence(oneTimePasscode);
        final VerificationSequence sequence2 = new SingleMethodSequence(OneTimePasscodeMother.eligible());
        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        final VerificationMethod method = sequences.getNextEligibleMethod(oneTimePasscode.getName());

        assertThat(method).isEqualTo(oneTimePasscode);
    }

    @Test
    void shouldThrowExceptionIfMethodIsNextMethodInTwoSequencesAndMethodIsTheDifferent() {
        final VerificationMethod oneTimePasscode = OneTimePasscodeMother.eligible();
        final VerificationSequence sequence1 = new SingleMethodSequence(oneTimePasscode);
        final VerificationSequence sequence2 = new SingleMethodSequence(OneTimePasscodeMother.eligible(DeliveryMethodMother.sms(UUID.randomUUID())));
        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        final Throwable error = catchThrowable(() -> sequences.getNextEligibleMethod(oneTimePasscode.getName()));

        final String expectedMessage = String.format("found multple instances of next method %s with different configurations", oneTimePasscode.getName());
        assertThat(error)
                .isInstanceOf(CannotDetermineWhichNextMethodToUseException.class)
                .hasMessage(expectedMessage);
    }

}
