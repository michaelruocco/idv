package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode.DeliveryMethodNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OneTimePasscodeEligibleTest {

    private final OneTimePasscodeParams params = OneTimePasscodeParamsMother.eligible();
    private final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();

    private final Collection<DeliveryMethod> deliveryMethods = Collections.singleton(deliveryMethod);

    private final OneTimePasscode method = new OneTimePasscodeEligible(params, deliveryMethods);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscode.NAME);
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
    void shouldReturnPasscodeLength() {
        assertThat(method.getPasscodeLength()).isEqualTo(params.getPasscodeLength());
    }

    @Test
    void shouldReturnMaxDeliveries() {
        assertThat(method.getMaxDeliveries()).isEqualTo(params.getMaxDeliveries());
    }

    @Test
    void shouldReturnPasscodeDuration() {
        assertThat(method.getPasscodeDuration()).isEqualTo(params.getPasscodeDuration());
    }

    @Test
    void shouldReturnDeliveryMethods() {
        assertThat(method.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

    @Test
    void shouldReturnDeliveryMethodById() {
        assertThat(method.getDeliveryMethod(deliveryMethod.getId())).isEqualTo(deliveryMethod);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodNotFoundById() {
        final UUID id = UUID.randomUUID();

        final Throwable error = catchThrowable(() -> method.getDeliveryMethod(id));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(id.toString());
    }

}
