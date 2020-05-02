package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class MobilePinsentryEligible implements VerificationMethod, MobilePinsentry {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final VerificationResults results;
    private final int maxAttempts;
    private final Duration duration;
    private final Eligibility eligibility;
    private final PinsentryFunction function;

    public MobilePinsentryEligible(final PinsentryFunction function) {
        this(function, new DefaultVerificationResults());
    }

    public MobilePinsentryEligible(final PinsentryFunction function, final VerificationResult result) {
        this(function, new DefaultVerificationResults(result));
    }

    public MobilePinsentryEligible(final PinsentryFunction function, final VerificationResults results) {
        this(function, results, MAX_ATTEMPTS, DURATION);
    }

    public MobilePinsentryEligible(final PinsentryFunction function,
                                   final int maxAttempts,
                                   final Duration duration) {
        this(function, new DefaultVerificationResults(), maxAttempts, duration);
    }

    public MobilePinsentryEligible(final PinsentryFunction function,
                                   final VerificationResults results,
                                   final int maxAttempts,
                                   final Duration duration) {
        this(results, maxAttempts, duration, new Eligible(), function);
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
        return eligibility.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return eligibility.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
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
    public VerificationMethod addResult(VerificationResult result) {
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, NAME, maxAttempts);
        return new MobilePinsentryEligible(updatedResults, maxAttempts, duration, eligibility, function);
    }

}
