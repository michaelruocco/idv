package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryEligibleTest {

    private final PinsentryParams params = PinsentryParamsMother.eligible();

    private final MobilePinsentry method = new MobilePinsentryEligible(params);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(params);
    }

    @Test
    void shouldBeEligible() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnResults() {
        assertThat(method.getResults()).isEqualTo(VerificationResultsMother.empty());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(params.getFunction());
    }

}
