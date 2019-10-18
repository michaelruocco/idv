package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardCredentialsIneligibleTest {

    private final VerificationMethod method = new CardCredentialsIneligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(CardCredentials.NAME);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoEligibleCards());
    }

}
