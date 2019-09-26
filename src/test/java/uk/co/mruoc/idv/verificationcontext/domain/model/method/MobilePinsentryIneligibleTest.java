package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryIneligibleTest {

    private final PinsentryFunction function = PinsentryFunction.IDENTIFY;

    private final MobilePinsentry method = new MobilePinsentryIneligible(function);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoMobileApplication());
    }

}
