package uk.co.idv.domain.entities.verificationcontext.method.cardcredentials;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

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
