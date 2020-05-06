package uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PushNotificationPolicy implements VerificationMethodPolicy {

    private final VerificationMethodParams params;

    @Override
    public String getName() {
        return PushNotification.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final VerificationMethodPolicyRequest request) {
        if (isEligible(request)) {
            return new PushNotificationEligible(params);
        }
        return new PushNotificationIneligible(new NoMobileApplication());
    }

    private boolean isEligible(final VerificationMethodPolicyRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

}
