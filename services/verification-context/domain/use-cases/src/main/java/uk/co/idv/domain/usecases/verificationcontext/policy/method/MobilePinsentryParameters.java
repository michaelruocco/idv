package uk.co.idv.domain.usecases.verificationcontext.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.time.Duration;
import java.util.Collection;

@RequiredArgsConstructor
public class MobilePinsentryParameters implements MethodPolicyParameters {

    private final PinsentryFunction function;
    private final int maxAttempts;
    private final Duration duration;

    @Override
    public String getMethodName() {
        return MobilePinsentry.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        if (isEligible(request)) {
            return new MobilePinsentryEligible(function, maxAttempts, duration);
        }
        return new MobilePinsentryIneligible(function);
    }

    private boolean isEligible(final LoadSequencesRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

}
