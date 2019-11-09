package uk.co.idv.domain.entities.verificationcontext.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleMobileNumbersTest {

    private final Ineligible ineligible = new NoEligibleMobileNumbers();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no eligible mobile numbers found");
    }

}
