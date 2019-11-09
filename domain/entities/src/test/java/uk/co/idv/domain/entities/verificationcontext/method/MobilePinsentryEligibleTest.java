package uk.co.idv.domain.entities.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryEligibleTest {

    private final PinsentryFunction function = PinsentryFunction.IDENTIFY;

    private final MobilePinsentryEligible method = new MobilePinsentryEligible(function);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(1);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(MobilePinsentry.NAME);

        final MobilePinsentryEligible methodWithResult = (MobilePinsentryEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
