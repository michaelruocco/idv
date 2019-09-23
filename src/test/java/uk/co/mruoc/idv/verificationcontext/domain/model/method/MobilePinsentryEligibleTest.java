package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryEligibleTest {

    private final PinsentryFunction function = PinsentryFunction.IDENTIFY;

    private final MobilePinsentry method = new MobilePinsentryEligible(function);

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
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

}
