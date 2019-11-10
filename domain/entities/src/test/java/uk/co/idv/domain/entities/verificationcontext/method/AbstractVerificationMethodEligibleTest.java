package uk.co.idv.domain.entities.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AbstractVerificationMethodEligibleTest {

    private static final String METHOD_NAME = "fake-method";

    private VerificationMethod method = new FakeVerificationMethodEligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnEligible() {
        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnEmptyEligibilityReason() {
        assertThat(method.getEligibilityReason()).isEmpty();
    }

    @Test
    void shouldReturnHasName() {
        assertThat(method.hasName(METHOD_NAME)).isTrue();
        assertThat(method.hasName("other-method-name")).isFalse();
    }

    @Test
    void shouldReturnHasResultsFalseIfMethodHasNoResults() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(2);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnHasResultsTrueIfMethodHasResults() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldReturnIsCompleteTrueIfContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIsCompleteFalseIfContainsFailedResultsLessThanMaxAttempts() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.isComplete()).isFalse();
    }

    @Test
    void shouldReturnIsCompleteTrueIfContainsFailedResultsEqualToOrGreaterThanMaxAttempts() {
        final VerificationResult result1 = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResult result2 = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result1, result2);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIsSuccessfulTrueIfContainsSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnIsSuccessfulFalseIfDoesNotContainSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnResults() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod methodWithResult = new FakeVerificationMethodEligible(results);

        assertThat(methodWithResult.getResults()).isEqualTo(results);
    }

    @Test
    void shouldThrowExceptionIfAttemptToAddResultThatDoesNotMatchMethod() {
        final VerificationResult result = new FakeVerificationResultFailed("other-method-name");

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToMethodException.class)
                .hasMessage("cannot add result for method other-method-name to method fake-method");
    }

    @Test
    void shouldThrowExceptionIfAttemptToAddResultToCompleteMethod() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final VerificationResults results = new DefaultVerificationResults(result);
        final VerificationMethod completeMethod = new FakeVerificationMethodEligible(results);

        final Throwable error = catchThrowable(() -> completeMethod.addResult(result));

        assertThat(error)
                .isInstanceOf(MethodAlreadyCompleteException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationMethod updatedMethod = method.addResult(result);

        assertThat(updatedMethod).isEqualToIgnoringGivenFields(method, "results");
        assertThat(updatedMethod.getResults()).containsExactly(result);
    }

}
