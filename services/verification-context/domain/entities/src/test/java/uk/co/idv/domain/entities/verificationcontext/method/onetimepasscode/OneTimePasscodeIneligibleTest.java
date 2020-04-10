package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

}
