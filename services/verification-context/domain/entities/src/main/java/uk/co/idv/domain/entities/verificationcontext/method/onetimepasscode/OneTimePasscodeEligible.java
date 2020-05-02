package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodUtils;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@EqualsAndHashCode
@RequiredArgsConstructor
public class OneTimePasscodeEligible implements VerificationMethod, OneTimePasscode {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final Eligibility ELIGIBLE = new Eligible();

    private final PasscodeSettings passcodeSettings;
    private final Collection<DeliveryMethod> deliveryMethods;
    private final int maxAttempts;
    private final Duration duration;
    private final VerificationResults results;

    public OneTimePasscodeEligible(final PasscodeSettings passcodeSettings,
                                   final Collection<DeliveryMethod> deliveryMethods) {
        this(passcodeSettings, deliveryMethods, new DefaultVerificationResults());
    }

    public OneTimePasscodeEligible(final PasscodeSettings passcodeSettings,
                                   final Collection<DeliveryMethod> deliveryMethods,
                                   final VerificationResult result) {
        this(passcodeSettings, deliveryMethods, new DefaultVerificationResults(result));
    }

    public OneTimePasscodeEligible(final PasscodeSettings passcodeSettings,
                                   final Collection<DeliveryMethod> deliveryMethods,
                                   final VerificationResults results) {
        this(passcodeSettings, deliveryMethods, MAX_ATTEMPTS, DURATION, results);
    }

    public OneTimePasscodeEligible(final PasscodeSettings passcodeSettings,
                                   final Collection<DeliveryMethod> deliveryMethods,
                                   final int maxAttempts,
                                   final Duration duration) {
        this(passcodeSettings, deliveryMethods, maxAttempts, duration, new DefaultVerificationResults());
    }

    public PasscodeSettings getPasscodeSettings() {
        return passcodeSettings;
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
        return passcodeSettings.getLength();
    }

    public int getMaxDeliveries() {
        return passcodeSettings.getMaxDeliveries();
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
    public VerificationMethod addResult(VerificationResult result) {
        final VerificationResults updatedResults = VerificationMethodUtils.addResult(results, result, NAME, maxAttempts);
        return new OneTimePasscodeEligible(passcodeSettings, deliveryMethods, maxAttempts, duration, updatedResults);
    }

    public static class DeliveryMethodNotFoundException extends RuntimeException {

        public DeliveryMethodNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

}
