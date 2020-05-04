package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
@ToString
public class OneTimePasscode implements VerificationMethod {

    public static final String NAME = "one-time-passcode";

    private final OneTimePasscodeParams params;
    private final Collection<DeliveryMethod> deliveryMethods;
    private final Eligibility eligibility;
    private final VerificationResults results;

    public static OneTimePasscode.OneTimePasscodeBuilder eligibleBuilder() {
        return OneTimePasscode.builder()
                .eligibility(new Eligible())
                .results(new DefaultVerificationResults());
    }

    public static OneTimePasscode.OneTimePasscodeBuilder ineligibleBuilder() {
        return OneTimePasscode.builder()
                .results(new DefaultVerificationResults());
    }

    public Collection<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public DeliveryMethod getDeliveryMethod(final UUID id) {
        return deliveryMethods.stream()
                .filter(method -> method.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DeliveryMethodNotFoundException(id));
    }

    public int getPasscodeLength() {
        return params.getPasscodeLength();
    }

    public int getMaxDeliveries() {
        return params.getMaxDeliveries();
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
    public void addResult(VerificationResult result) {
        if (!isEligible()) {
            throw new CannotAddResultToIneligibleMethodException(NAME);
        }
        VerificationMethodUtils.addResult(results, result, NAME, params.getMaxAttempts());
    }

    public static class DeliveryMethodNotFoundException extends RuntimeException {

        public DeliveryMethodNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

}
