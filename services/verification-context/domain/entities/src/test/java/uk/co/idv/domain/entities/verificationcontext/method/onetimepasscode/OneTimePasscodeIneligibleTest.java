package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class OneTimePasscodeIneligibleTest {

    private final OneTimePasscode method = new OneTimePasscodeIneligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoEligibleDeliveryMethods());
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
    void shouldHaveNoEligibleDeliveryMethodsReason() {
        assertThat(method.getEligibilityReason()).contains("no eligible delivery methods found");
    }

    @Test
    void shouldHaveMobilePinsentryName() {
        assertThat(method.hasName(OneTimePasscode.NAME)).isTrue();
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
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(OneTimePasscode.NAME);
    }

}
