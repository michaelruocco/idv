package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

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
