package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OneTimePasscodeIneligibleTest {

    private final Ineligible ineligible = mock(Ineligible.class);
    private final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();

    private final Collection<DeliveryMethod> deliveryMethods = Collections.singleton(deliveryMethod);

    private final OneTimePasscode method = new OneTimePasscodeIneligible(ineligible, deliveryMethods);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(OneTimePasscodeParamsMother.ineligible());
    }

    @Test
    void shouldBeIneligible() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

    @Test
    void shouldReturnResults() {
        assertThat(method.getResults()).isEqualTo(VerificationResultsMother.empty());
    }

}
