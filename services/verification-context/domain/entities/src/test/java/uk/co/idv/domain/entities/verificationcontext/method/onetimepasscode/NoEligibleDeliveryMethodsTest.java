package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleDeliveryMethodsTest {

    private final Ineligible ineligible = new NoEligibleDeliveryMethods();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no eligible delivery methods found");
    }

}
