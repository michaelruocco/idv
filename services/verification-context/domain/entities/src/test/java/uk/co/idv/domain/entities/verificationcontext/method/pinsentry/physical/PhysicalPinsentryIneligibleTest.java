package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

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
