package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.CannotCalculateMaxDurationOfEmptySequencesException;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NoSequencesFoundWithNextMethodException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethod;
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
    void shouldThrowExceptionIfAddingResultWithMethodThatIsNotNextMethodInSequence() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationSequence sequence1 = new SingleMethodSequence(new FakeVerificationMethod("other-name-1"));
        final VerificationSequence sequence2 = new SingleMethodSequence(new FakeVerificationMethod("other-name-2"));

        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        final Throwable error = catchThrowable(() -> sequences.addResultIfHasSequencesWithNextMethod(result));

        assertThat(error)
                .isInstanceOf(NoSequencesFoundWithNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldAddResultToSequencesWithMethodThatIsNextMethodInSequence() {
        final String methodName = "method-name";
        final VerificationResult result = new FakeVerificationResultSuccessful(methodName);
        final VerificationSequence sequence1 = new SingleMethodSequence(new FakeVerificationMethod(methodName));
        final VerificationSequence sequence2 = new SingleMethodSequence(new FakeVerificationMethod(methodName));
        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);

        final VerificationSequences updatedSequences = sequences.addResultIfHasSequencesWithNextMethod(result);

        assertThat(updatedSequences).hasSameSizeAs(sequences);
        final VerificationSequence updatedSequence1 = updatedSequences.get(0);
        assertThat(updatedSequence1).isEqualToIgnoringGivenFields(sequence1, "results");
        assertThat(updatedSequence1.getResults()).containsExactly(result);
        final VerificationSequence updatedSequence2 = updatedSequences.get(1);
        assertThat(updatedSequence2).isEqualToIgnoringGivenFields(sequence2, "results");
        assertThat(updatedSequence2.getResults()).containsExactly(result);
    }

}
