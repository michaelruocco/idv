package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultsAlwaysEmptyTest {

    private static final String METHOD_NAME = "method-name";

    @Test
    void shouldReturnIsEmpty() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnEmptyStream() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.stream()).isEmpty();
    }

    @Test
    void shouldNotContainSuccessful() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.containsSuccessful()).isFalse();
    }

    @Test
    void shouldNotContainSuccessfulMethod() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.containsSuccessful(METHOD_NAME)).isFalse();
    }

    @Test
    void shouldNotReturnAnyMethodNames() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.getMethodNames()).isEmpty();
    }

    @Test
    void shouldReturnZeroSize() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnSelfWithAddingResultIfAttemptToAddResult() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults originalResults = new VerificationResultsAlwaysEmpty();

        final VerificationResults newResults = originalResults.add(result);

        assertThat(newResults.add(result)).isSameAs(originalResults);
        assertThat(newResults.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnEmptyIterator() {
        final VerificationResults results = new VerificationResultsAlwaysEmpty();

        assertThat(results.iterator()).isEqualTo(Collections.emptyIterator());
    }

}
