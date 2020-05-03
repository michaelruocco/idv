package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Collection;

@RequiredArgsConstructor
public class MobilePinsentryPolicy implements MethodPolicy {

    private final PinsentryParams params;

    @Override
    public String getName() {
        return MobilePinsentry.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        if (isEligible(request)) {
            return eligible();
        }
        return ineligible();
    }

    private boolean isEligible(final LoadSequencesRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

    private VerificationMethod ineligible() {
        return MobilePinsentry.ineligibleBuilder()
                .params(new IneligiblePinsentryParams(params.getFunction()))
                .eligibility(new NoMobileApplication())
                .build();
    }

    private VerificationMethod eligible() {
        return MobilePinsentry.eligibleBuilder()
                .params(params)
                .build();
    }

}
