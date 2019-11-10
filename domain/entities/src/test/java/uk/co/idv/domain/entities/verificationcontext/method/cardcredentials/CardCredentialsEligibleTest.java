package uk.co.idv.domain.entities.verificationcontext.method.cardcredentials;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class CardCredentialsEligibleTest {

    private final VerificationMethod method = new CardCredentialsEligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(CardCredentials.NAME);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(1);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(CardCredentials.NAME);

        final CardCredentialsEligible methodWithResult = (CardCredentialsEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
