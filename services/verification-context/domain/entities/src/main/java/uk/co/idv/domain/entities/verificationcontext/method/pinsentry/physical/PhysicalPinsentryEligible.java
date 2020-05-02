package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class PhysicalPinsentryEligible implements VerificationMethod, PhysicalPinsentry {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final Eligibility ELIGIBLE = new Eligible();

    private final VerificationResults results;
    private final int maxAttempts;
    private final Duration duration;
    private final PinsentryFunction function;
    private final Collection<CardNumber> cardNumbers;

    @Builder
    public PhysicalPinsentryEligible(final PinsentryFunction function, final Collection<CardNumber> cardNumbers) {
        this(function, cardNumbers, new DefaultVerificationResults());
    }

    public PhysicalPinsentryEligible(final PinsentryFunction function,
                                     final Collection<CardNumber> cardNumbers,
                                     final VerificationResult result) {
        this(function, cardNumbers, new DefaultVerificationResults(result));
    }

    public PhysicalPinsentryEligible(final PinsentryFunction function,
                                     final Collection<CardNumber> cardNumbers,
                                     final VerificationResults results) {
        this(results, MAX_ATTEMPTS, DURATION, function, cardNumbers);
    }

    public PhysicalPinsentryEligible(final PinsentryFunction function,
                                     final int maxAttempts,
                                     final Duration duration,
                                     final Collection<CardNumber> cardNumbers) {
        this(new DefaultVerificationResults(), maxAttempts, duration, function, cardNumbers);
    }

    public Collection<CardNumber> getCardNumbers() {
        return cardNumbers;
    }

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean isEligible() {
        return ELIGIBLE.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return ELIGIBLE.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return ELIGIBLE;
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public boolean isComplete() {
        return VerificationMethodUtils.isComplete(results, maxAttempts);
    }

    @Override
    public boolean isSuccessful() {
        return results.containsSuccessful();
    }

    @Override
    public VerificationResults getResults() {
        return results;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, NAME, maxAttempts);
        return new PhysicalPinsentryEligible(updatedResults, maxAttempts, duration, function, cardNumbers);
    }
}
