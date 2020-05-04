package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DefaultVerificationMethod implements VerificationMethod {

    private final String name;
    private final VerificationMethodParams params;
    private final Eligibility eligibility;
    private final VerificationResults results;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxAttempts() {
        return params.getMaxAttempts();
    }

    @Override
    public Duration getDuration() {
        return params.getDuration();
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
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public boolean isComplete() {
        if (!isEligible()) {
            return false;
        }
        return VerificationMethodUtils.isComplete(results, params.getMaxAttempts());
    }

    @Override
    public boolean isSuccessful() {
        return results.containsSuccessful();
    }

    @Override
    public void addResult(final VerificationResult result) {
        if (!isEligible()) {
            throw new CannotAddResultToIneligibleMethodException(name);
        }
        VerificationMethodUtils.addResult(results, result, name, params.getMaxAttempts());
    }

}
