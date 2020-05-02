package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class PhysicalPinsentryIneligibleTest {

    private static final Ineligible INELIGIBLE = new NoPinsentryDevice();
    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    private final PhysicalPinsentryIneligible method = PhysicalPinsentryIneligible.builder()
            .ineligible(INELIGIBLE)
            .function(FUNCTION)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(INELIGIBLE);
    }

    @Test
    void shouldHaveZeroMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldHaveZeroDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldNotBeEligible() {
        assertThat(method.isEligible()).isFalse();
    }

    @Test
    void shouldReturnIneligibleReason() {
        assertThat(method.getEligibilityReason()).isEqualTo(INELIGIBLE.getReason());
    }

    @Test
    void shouldHaveMobilePinsentryName() {
        assertThat(method.hasName(PhysicalPinsentry.NAME)).isTrue();
    }

    @Test
    void shouldNotHaveAnyOtherName() {
        assertThat(method.hasName("other")).isFalse();
    }

    @Test
    void shouldNotHaveResults() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldNotBeComplete() {
        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeSuccessful() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnAlwaysEmptyResults() {
        assertThat(method.getResults()).isInstanceOf(VerificationResultsAlwaysEmpty.class);
    }

    @Test
    void shouldThrowExceptionIfAttemptToAddResult() {
        final VerificationResult result = mock(VerificationResult.class);

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(VerificationMethod.CannotAddResultToIneligibleMethodException.class)
                .hasMessage(PhysicalPinsentry.NAME);
    }

}
