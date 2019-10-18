package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.time.Duration;
import java.time.Instant;

public class MaxDurationExpiryCalculator implements ExpiryCalculator {

    @Override
    public Instant calculateExpiry(final CalculateExpiryRequest request) {
        final VerificationSequences sequences = request.getSequences();
        final Instant created = request.getCreated();
        if (sequences.isEmpty()) {
            return created;
        }
        final Duration maxDuration = sequences.calculateMaxDuration();
        return created.plus(maxDuration);
    }

}
