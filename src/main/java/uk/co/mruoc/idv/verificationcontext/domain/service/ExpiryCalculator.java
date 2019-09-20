package uk.co.mruoc.idv.verificationcontext.domain.service;

import java.time.Instant;

public interface ExpiryCalculator {

    Instant calculateExpiry(CalculateExpiryRequest request);

}
