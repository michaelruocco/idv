package uk.co.idv.domain.usecases.verificationcontext.expiry;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.CalculateExpiryRequest;
import uk.co.idv.domain.usecases.verificationcontext.ExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.expiry.MaxDurationExpiryCalculator;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MaxDurationExpiryCalculatorTest {

    private final Instant created = Instant.now();
    private final VerificationSequences sequences = mock(VerificationSequences.class);
    private final CalculateExpiryRequest request = buildRequest();

    private final ExpiryCalculator calculator = new MaxDurationExpiryCalculator();

    @Test
    void shouldReturnCreatedIfSequencesIsEmpty() {
        given(sequences.isEmpty()).willReturn(true);

        final Instant expiry = calculator.calculateExpiry(request);

        assertThat(expiry).isEqualTo(created);
    }

    @Test
    void shouldReturnCreatedPlusMaxDurationIfSequencesIsNotEmpty() {
        final Duration maxDuration = Duration.ofMinutes(5);
        given(sequences.isEmpty()).willReturn(false);
        given(sequences.calculateMaxDuration()).willReturn(maxDuration);

        final Instant expiry = calculator.calculateExpiry(request);

        assertThat(expiry).isEqualTo(created.plus(maxDuration));
    }

    private CalculateExpiryRequest buildRequest() {
        return CalculateExpiryRequest.builder()
                .sequences(sequences)
                .created(created)
                .build();
    }

}
