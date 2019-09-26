package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryIneligibleTest {

    private final Ineligible ineligible = new NoPinsentryDevice();
    private final PinsentryFunction function = PinsentryFunction.RESPOND;

    private final PhysicalPinsentryIneligible method = PhysicalPinsentryIneligible.builder()
            .ineligible(ineligible)
            .function(function)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

}
