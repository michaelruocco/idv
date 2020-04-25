package uk.co.idv.domain.usecases.verificationcontext.expiry;


import uk.co.idv.domain.usecases.verificationcontext.CalculateExpiryRequest;
import uk.co.idv.domain.usecases.verificationcontext.ExpiryCalculator;

import java.time.Duration;
import java.time.Instant;

public class FakeExpiryCalculator implements ExpiryCalculator {

    private final Duration duration;

    private CalculateExpiryRequest lastRequest;

    public FakeExpiryCalculator(final Duration duration) {
        this.duration = duration;
    }

    @Override
    public Instant calculateExpiry(CalculateExpiryRequest request) {
        this.lastRequest = request;
        return request.getCreated().plus(duration);
    }

    public CalculateExpiryRequest getLastRequest() {
        return lastRequest;
    }

}
