package uk.co.idv.domain.entities.verificationcontext.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultsTest {

    private static final String METHOD_NAME = "fake-method";

    @Test
    void shouldReturnIsEmptyTrueIfContainsNoResults() {
        final VerificationResults results = new DefaultVerificationResults();

        assertThat(results.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnIsEmptyFalseIfContainsResults() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnContainsSuccessfulTrueIfContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.containsSuccessful()).isTrue();
    }

    @Test
    void shouldReturnContainsSuccessfulFalseIfDoesNotContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationResults results = new DefaultVerificationResults(result);

        assertThat(results.isEmpty()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result1);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME);

        results.add(result2);

        assertThat(results).containsExactly(result1, result2);
    }

    @Test
    void shouldReturnSize() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result1, result2);

        assertThat(results.size()).isEqualTo(2);
    }

}
