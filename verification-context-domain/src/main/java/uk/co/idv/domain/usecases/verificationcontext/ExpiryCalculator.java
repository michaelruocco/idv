package uk.co.idv.domain.usecases.verificationcontext;

import java.time.Instant;

public interface ExpiryCalculator {

    Instant calculateExpiry(CalculateExpiryRequest request);

}
