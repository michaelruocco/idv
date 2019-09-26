package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.CannotCalculateMaxDurationOfEmptySequencesException;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NoSequencesFoundWithNextMethodEligibleException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;

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
    void shouldThrowExceptionIfAttemptToCalculateMaxDurationOfEmptySequences() {
        final VerificationSequences sequences = new VerificationSequences();

        final Throwable error = catchThrowable(sequences::calculateMaxDuration);

        assertThat(error).isInstanceOf(CannotCalculateMaxDurationOfEmptySequencesException.class);
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
    void shouldCalculateMaxDuration() {
        final VerificationSequence sequence1 = mock(VerificationSequence.class);
        given(sequence1.getDuration()).willReturn(Duration.ofMinutes(5));

        final VerificationSequence sequence2 = mock(VerificationSequence.class);
        given(sequence2.getDuration()).willReturn(Duration.ofMinutes(10));

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        assertThat(sequences.calculateMaxDuration()).isEqualTo(Duration.ofMinutes(10));
    }

    @Test
    void shouldThrowExceptionIfAddingResultWithMethodThatIsNextMethodInSequenceButNotEligible() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final VerificationMethod method = new FakeVerificationMethodIneligible(result.getMethodName());
        final VerificationSequence sequence = new SingleMethodSequence(method);

        final VerificationSequences sequences = new VerificationSequences(sequence);

        final Throwable error = catchThrowable(() -> sequences.addResultIfHasSequencesWithNextMethod(result));

        assertThat(error)
                .isInstanceOf(NoSequencesFoundWithNextMethodEligibleException.class)
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
                .isInstanceOf(NoSequencesFoundWithNextMethodEligibleException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldAddResultToMethodThatIsNextMethodInAnySequences() {
        final String methodName = "method-name";
        final VerificationMethod method1 = new FakeVerificationMethodEligible(methodName);
        final VerificationMethod method2 = new FakeVerificationMethodEligible(methodName);
        final VerificationSequence sequence1 = new SingleMethodSequence(method1);
        final VerificationSequence sequence2 = new SingleMethodSequence(method2);
        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);
        final VerificationResult result = new FakeVerificationResultSuccessful(methodName);

        final VerificationSequences updatedSequences = sequences.addResultIfHasSequencesWithNextMethod(result);

        assertThat(updatedSequences.getResults(sequence1.getName(), method1.getName())).containsExactly(result);
        assertThat(updatedSequences.getResults(sequence2.getName(), method2.getName())).containsExactly(result);
    }

}
