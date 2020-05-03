package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Collection;

@RequiredArgsConstructor
public class PushNotificationPolicy implements MethodPolicy {

    private final VerificationMethodParams params;

    @Override
    public String getName() {
        return PushNotification.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        if (isEligible(request)) {
            return PushNotification.eligibleBuilder()
                    .params(params)
                    .build();
        }
        return PushNotification.ineligible(new NoMobileApplication());
    }

    private boolean isEligible(final LoadSequencesRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

}
