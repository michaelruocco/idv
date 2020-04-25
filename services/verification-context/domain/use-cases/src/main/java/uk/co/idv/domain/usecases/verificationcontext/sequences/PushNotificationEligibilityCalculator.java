package uk.co.idv.domain.usecases.verificationcontext.sequences;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.usecases.verificationcontext.LoadSequencesRequest;

import java.util.Collection;

@RequiredArgsConstructor
public class PushNotificationEligibilityCalculator implements EligibilityCalculator {

    private final PushNotificationParameters parameters;

    @Override
    public VerificationMethod calculate(final LoadSequencesRequest request) {
        final Identity identity = request.getIdentity();
        if (isEligible(identity.getMobileDevices())) {
            return parameters.buildEligibleMethod();
        }
        return new PushNotificationIneligible();
    }

    private boolean isEligible(final Collection<MobileDevice> devices) {
        return devices.stream().anyMatch(MobileDevice::isTrusted);
    }

}
