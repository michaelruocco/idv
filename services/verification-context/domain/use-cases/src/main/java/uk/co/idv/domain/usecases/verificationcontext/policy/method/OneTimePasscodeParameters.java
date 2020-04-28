package uk.co.idv.domain.usecases.verificationcontext.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OneTimePasscodeParameters implements MethodPolicyParameters {

    private final IdGenerator idGenerator;
    private final PasscodeSettings settings;
    private final int maxAttempts;
    private final Duration duration;

    @Override
    public String getMethodName() {
        return OneTimePasscode.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        final Collection<DeliveryMethod> deliveryMethods = toDeliveryMethods(request);
        if (deliveryMethods.isEmpty()) {
            return new OneTimePasscodeIneligible();
        }
        return new OneTimePasscodeEligible(settings, deliveryMethods, maxAttempts, duration);
    }

    private Collection<DeliveryMethod> toDeliveryMethods(final LoadSequencesRequest request) {
        final PhoneNumbers phoneNumbers = request.getPhoneNumbers();
        return phoneNumbers.stream()
                .filter(PhoneNumber::isMobile)
                .map(this::toDeliveryMethod)
                .collect(Collectors.toList());
    }

    private DeliveryMethod toDeliveryMethod(final PhoneNumber phoneNumber) {
        return new SmsDeliveryMethod(idGenerator.generate(), phoneNumber.getValue());
    }

}
