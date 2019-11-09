package uk.co.idv.domain.entities.verificationcontext.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultsTest {

    private static final String METHOD_NAME_1 = "fake-method-1";
    private static final String METHOD_NAME_2 = "fake-method-2";

    @Test
    void shouldReturnIsEmptyTrueIfContainsNoResults() {
        final VerificationResults results = new DefaultVerificationResults();

        assertThat(results.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnIsEmptyFalseIfContainsResults() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME_1);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnContainsSuccessfulTrueIfContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME_1);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.containsSuccessful()).isTrue();
    }

    @Test
    void shouldReturnContainsSuccessfulFalseIfDoesNotContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME_1);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnMethodNames() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME_1);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME_2);

        final VerificationResults results = new DefaultVerificationResults(result1, result2);

        assertThat(results.getMethodNames()).containsExactly(METHOD_NAME_1, METHOD_NAME_2);
    }

    @Test
    void shouldReturnHasSuccessfulTrueIfContainsSuccessfulResultWithName() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME_1);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.containsSuccessful(METHOD_NAME_1)).isTrue();
    }

    @Test
    void shouldReturnHasSuccessfulFalseIfDoesNotContainsSuccessfulResultWithName() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME_1);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.containsSuccessful(METHOD_NAME_1)).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME_1);
        final VerificationResults results = new DefaultVerificationResults(result1);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME_1);

        final VerificationResults updatedResults = results.add(result2);

        assertThat(updatedResults).containsExactly(result1, result2);
    }

    @Test
    void shouldReturnSize() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME_1);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME_1);
        final VerificationResults results = new DefaultVerificationResults(result1, result2);

        assertThat(results.size()).isEqualTo(2);
    }

}
