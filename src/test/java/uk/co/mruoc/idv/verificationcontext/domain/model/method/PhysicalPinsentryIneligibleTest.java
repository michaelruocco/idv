package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryIneligibleTest {

    private final Ineligible ineligible = new NoPinsentryDevice();
    private final PinsentryFunction function = PinsentryFunction.RESPOND;

    private final PhysicalPinsentry method = PhysicalPinsentryIneligible.builder()
            .ineligible(ineligible)
            .function(function)
            .build();

    @Test
    void shouldReturnIneligibleEligibility() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

    @Test
    void shouldReturnEmptyCardNumbers() {
        assertThat(method.getCardNumbers()).isEmpty();
    }

}
