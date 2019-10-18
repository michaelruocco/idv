package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AbstractVerificationMethodIneligibleTest {

    private static final String METHOD_NAME = "fake-method";

    private VerificationMethod method = new FakeVerificationMethodIneligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnIneligible() {
        assertThat(method.isEligible()).isFalse();
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new FakeIneligible());
    }

    @Test
    void shouldReturnEligibilityReason() {
        assertThat(method.getEligibilityReason()).isEqualTo(new FakeIneligible().getReason());
    }

    @Test
    void shouldReturnHasName() {
        assertThat(method.hasName(METHOD_NAME)).isTrue();
        assertThat(method.hasName("other-method-name")).isFalse();
    }

    @Test
    void shouldReturnHasResultsFalse() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldReturnMaxAttemptsZero() {
        assertThat(method.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldReturnDurationZero() {
        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnIsCompleteFalse() {
        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldReturnIsSuccessfulFalse() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnAlwaysEmptyResults() {
        assertThat(method.getResults()).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfAttemptToAddResultToIneligible() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(result.getMethodName());
    }

}
