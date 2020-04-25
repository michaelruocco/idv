package uk.co.idv.domain.usecases.verificationcontext.expiry;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verificationcontext.CalculateExpiryRequest;
import uk.co.idv.domain.usecases.verificationcontext.ExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.expiry.FixedDurationExpiryCalculator;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class FixedDurationExpiryCalculatorTest {

    private final Instant created = Instant.now();
    private final CalculateExpiryRequest request = buildRequest();

    @Test
    void shouldReturnCreatedDatePlusFixedDurationOfFiveMinutesIfNoDurationProvided() {
        final ExpiryCalculator calculator = new FixedDurationExpiryCalculator();

        final Instant expiry = calculator.calculateExpiry(request);

        assertThat(expiry).isEqualTo(created.plus(Duration.ofMinutes(5)));
    }

    @Test
    void shouldReturnCreatedDatePlusFixedDuration() {
        final Duration duration = Duration.ofMinutes(1);
        final ExpiryCalculator calculator = new FixedDurationExpiryCalculator(duration);

        final Instant expiry = calculator.calculateExpiry(request);

        assertThat(expiry).isEqualTo(created.plus(duration));
    }

    private CalculateExpiryRequest buildRequest() {
        return CalculateExpiryRequest.builder()
                .created(created)
                .build();
    }

}
