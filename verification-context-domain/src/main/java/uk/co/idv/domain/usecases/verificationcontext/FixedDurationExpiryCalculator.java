package uk.co.idv.domain.usecases.verificationcontext;

import java.time.Duration;
import java.time.Instant;

public class FixedDurationExpiryCalculator implements ExpiryCalculator {

    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(5);

    private final Duration duration;

    public FixedDurationExpiryCalculator() {
        this(DEFAULT_DURATION);
    }

    public FixedDurationExpiryCalculator(final Duration duration) {
        this.duration = duration;
    }

    @Override
    public Instant calculateExpiry(final CalculateExpiryRequest request) {
        final Instant created = request.getCreated();
        return created.plus(duration);
    }

}
