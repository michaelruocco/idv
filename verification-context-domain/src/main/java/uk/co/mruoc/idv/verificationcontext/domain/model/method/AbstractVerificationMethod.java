package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

public abstract class AbstractVerificationMethod implements VerificationMethod {

    private final String name;
    private final VerificationResults results;
    private final int maxAttempts;
    private final Duration duration;
    private final Eligibility eligibility;

    public AbstractVerificationMethod(final String name,
                                      final VerificationResults results,
                                      final int maxAttempts,
                                      final Duration duration,
                                      final Eligibility eligibility) {
        this.name = name;
        this.results = results;
        this.maxAttempts = maxAttempts;
        this.duration = duration;
        this.eligibility = eligibility;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

    @Override
    public boolean isEligible() {
        return eligibility.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return eligibility.getReason();
    }

    public boolean hasName(final String otherName) {
        return name.equals(otherName);
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public boolean isComplete() {
        if (results.containsSuccessful()) {
            return true;
        }
        return results.size() >= maxAttempts;
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
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        if (!name.equals(result.getMethodName())) {
            throw new CannotAddResultToMethodException(result.getMethodName(), name);
        }
        if (isComplete()) {
            throw new CannotAddResultToCompleteMethodException(name);
        }
        return updateResults(results.add(result));
    }

    protected abstract VerificationMethod updateResults(final VerificationResults results);

}
