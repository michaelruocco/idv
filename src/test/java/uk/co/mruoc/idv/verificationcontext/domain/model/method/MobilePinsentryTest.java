package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryTest {

    private final Eligibility eligibility = new Eligible();
    private final PinsentryFunction function = PinsentryFunction.IDENTIFY;

    private final MobilePinsentry method = new MobilePinsentry(eligibility, function);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("mobile-pinsentry");
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

}
