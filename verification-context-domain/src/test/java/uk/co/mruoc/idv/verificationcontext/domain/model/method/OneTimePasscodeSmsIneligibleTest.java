package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSmsIneligibleTest {

    private final OneTimePasscodeSms method = new OneTimePasscodeSmsIneligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscodeSms.NAME);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoEligibleMobileNumbers());
    }

}
