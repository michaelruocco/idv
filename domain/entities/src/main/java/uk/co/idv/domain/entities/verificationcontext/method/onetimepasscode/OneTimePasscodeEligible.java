package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class OneTimePasscodeEligible extends AbstractVerificationMethodEligible implements OneTimePasscode {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PasscodeSettings passcodeSettings;
    private final Collection<DeliveryMethod> deliveryMethods;

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
        super(NAME, results, MAX_ATTEMPTS, DURATION);
        this.passcodeSettings = passcodeSettings;
        this.deliveryMethods = deliveryMethods;
    }

    public PasscodeSettings getPasscodeSettings() {
        return passcodeSettings;
    }

    public Collection<DeliveryMethod> getDeliveryMethods() {
        return Collections.unmodifiableCollection(deliveryMethods);
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new OneTimePasscodeEligible(passcodeSettings, deliveryMethods, results);
    }

}
