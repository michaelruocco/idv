package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.NoEligibleDeliveryMethods;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OneTimePasscodePolicy implements MethodPolicy {

    private final OneTimePasscodeParams params;

    @Override
    public String getName() {
        return OneTimePasscode.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        final Collection<DeliveryMethod> deliveryMethods = toDeliveryMethods(request);
        if (deliveryMethods.isEmpty()) {
            return ineligible(deliveryMethods);
        }
        return eligible(deliveryMethods);
    }

    private Collection<DeliveryMethod> toDeliveryMethods(final LoadSequencesRequest request) {
        final PhoneNumbers phoneNumbers = request.getPhoneNumbers();
        return phoneNumbers.stream()
                .filter(PhoneNumber::isMobile)
                .map(this::toDeliveryMethod)
                .collect(Collectors.toList());
    }

    private DeliveryMethod toDeliveryMethod(final PhoneNumber phoneNumber) {
        return new SmsDeliveryMethod(phoneNumber.getId(), phoneNumber.getValue());
    }

    private static VerificationMethod ineligible(final Collection<DeliveryMethod> deliveryMethods) {
        return new OneTimePasscodeIneligible(new NoEligibleDeliveryMethods(), deliveryMethods);
    }

    private VerificationMethod eligible(final Collection<DeliveryMethod> deliveryMethods) {
        return new OneTimePasscodeEligible(params, deliveryMethods);
    }

}
