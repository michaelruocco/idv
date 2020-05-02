package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.FakeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class FakeVerificationMethodIneligible implements VerificationMethod, FakeVerificationMethod {


    private final VerificationResults RESULTS = new VerificationResultsAlwaysEmpty();

    private final String name;
    private final Ineligible ineligible;

    public FakeVerificationMethodIneligible() {
        this(new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final String name) {
        this(name, new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final Ineligible ineligible) {
        this(NAME, ineligible);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxAttempts() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    public boolean isEligible() {
        return ineligible.isEligible();
    }

    @Override
    public Optional<String> getEligibilityReason() {
        return ineligible.getReason();
    }

    @Override
    public Eligibility getEligibility() {
        return ineligible;
    }

    @Override
    public boolean hasResults() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public VerificationResults getResults() {
        return RESULTS;
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        throw new CannotAddResultToIneligibleMethodException(name);
    }

}
