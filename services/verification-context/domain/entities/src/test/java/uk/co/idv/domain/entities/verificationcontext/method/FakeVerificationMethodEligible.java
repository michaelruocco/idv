package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class FakeVerificationMethodEligible implements VerificationMethod, FakeVerificationMethod {

    private static final int MAX_ATTEMPTS = 2;
    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final Eligibility ELIGIBLE = new Eligible();

    private final String name;
    private final VerificationResults results;
    private final int maxAttempts;
    private final Duration duration;

    public FakeVerificationMethodEligible() {
        this(new DefaultVerificationResults());
    }

    public FakeVerificationMethodEligible(final String name) {
        this(name, new DefaultVerificationResults(), MAX_ATTEMPTS, DURATION);
    }

    public FakeVerificationMethodEligible(final Duration duration) {
        this(NAME, new DefaultVerificationResults(), duration);
    }

    public FakeVerificationMethodEligible(final VerificationResult result) {
        this(result.getMethodName(), new DefaultVerificationResults(result), DURATION);
    }

    public FakeVerificationMethodEligible(final VerificationResults results) {
        this(NAME, results, DURATION);
    }

    public FakeVerificationMethodEligible(final String name, final VerificationResults results, final Duration duration) {
        this(name, results, MAX_ATTEMPTS, duration);
    }

    @Override
    public String getName() {
        return name;
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
    public VerificationMethod addResult(VerificationResult result) {
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, name, maxAttempts);
        return new FakeVerificationMethodEligible(name, updatedResults, maxAttempts, duration);
    }

}
