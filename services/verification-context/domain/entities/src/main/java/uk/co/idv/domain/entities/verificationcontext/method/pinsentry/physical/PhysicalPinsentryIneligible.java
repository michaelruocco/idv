package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;
import java.util.Optional;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
public class PhysicalPinsentryIneligible implements VerificationMethod, PhysicalPinsentry {

    private static final VerificationResults RESULTS = new VerificationResultsAlwaysEmpty();

    private final Ineligible ineligible;
    private final PinsentryFunction function;

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
    public VerificationMethod addResult(VerificationResult result) {
        throw new CannotAddResultToIneligibleMethodException(NAME);
    }

}
