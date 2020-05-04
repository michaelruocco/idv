package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OneTimePasscode extends DefaultVerificationMethod {

    public static final String NAME = "one-time-passcode";

    private final OneTimePasscodeParams params;
    private final Collection<DeliveryMethod> deliveryMethods;

    public OneTimePasscode(final OneTimePasscodeParams params,
                           final Eligibility eligibility,
                           final VerificationResults results,
                           final Collection<DeliveryMethod> deliveryMethods) {
        super(NAME, params, eligibility, results);
        this.params = params;
        this.deliveryMethods = deliveryMethods;
    }

    public int getPasscodeLength() {
        return params.getPasscodeLength();
    }

    public int getMaxDeliveries() {
        return params.getMaxDeliveries();
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

    public static class DeliveryMethodNotFoundException extends RuntimeException {

        public DeliveryMethodNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

}
