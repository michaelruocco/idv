package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleMobileNumbersTest {

    private final Ineligible ineligible = new NoEligibleMobileNumbers();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no eligible mobile numbers found");
    }

}
