package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MaxDurationExpiryCalculator implements ExpiryCalculator {

    @Override
    public Instant calculateExpiry(final CalculateExpiryRequest request) {
        final Collection<VerificationSequence> sequences = request.getSequences();
        final Instant created = request.getCreated();
        if (sequences.isEmpty()) {
            return created;
        }
        final Duration maxDuration = extractMaxDuration(sequences);
        return created.plus(maxDuration);
    }

    private Duration extractMaxDuration(final Collection<VerificationSequence> sequences) {
        final List<Duration> durations = sequences.stream()
                .map(VerificationSequence::getDuration)
                .sorted()
                .collect(Collectors.toList());
        return durations.get(durations.size() - 1);
    }

}
