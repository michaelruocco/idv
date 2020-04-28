package uk.co.idv.domain.usecases.verificationcontext.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.time.Duration;
import java.util.Collection;

@RequiredArgsConstructor
public class PushNotificationPolicy implements MethodPolicy {

    private final int maxAttempts;
    private final Duration duration;

    @Override
    public String getName() {
        return PushNotification.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        if (isEligible(request)) {
            return new PushNotificationEligible(maxAttempts, duration);
        }
        return new PushNotificationIneligible();
    }

    private boolean isEligible(final LoadSequencesRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

}
