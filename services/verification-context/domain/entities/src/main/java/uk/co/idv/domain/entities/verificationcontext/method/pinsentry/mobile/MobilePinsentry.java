package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class MobilePinsentry implements VerificationMethod {

    public static final String NAME = "mobile-pinsentry";

    private final PinsentryParams params;
    private final Eligibility eligibility;
    private final VerificationResults results;

    public static MobilePinsentry.MobilePinsentryBuilder eligibleBuilder() {
        return MobilePinsentry.builder()
                .eligibility(new Eligible())
                .results(new DefaultVerificationResults());
    }

    public static MobilePinsentry.MobilePinsentryBuilder ineligibleBuilder() {
        return MobilePinsentry.builder()
                .eligibility(new NoMobileApplication())
                .results(new DefaultVerificationResults());
    }

    @Override
    public String getName() {
        return NAME;
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
    public VerificationMethod addResult(final VerificationResult result) {
        final int maxAttempts = params.getMaxAttempts();
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, NAME, maxAttempts);
        return new MobilePinsentry(params, eligibility, updatedResults);
    }

    public PinsentryFunction getFunction() {
        return params.getFunction();
    }

}
