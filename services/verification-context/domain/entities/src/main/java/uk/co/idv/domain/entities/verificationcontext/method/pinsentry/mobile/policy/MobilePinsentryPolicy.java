package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;

import java.util.Collection;

@RequiredArgsConstructor
@Data
public class MobilePinsentryPolicy implements VerificationMethodPolicy {

    private final PinsentryParams params;

    @Override
    public String getName() {
        return MobilePinsentry.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final VerificationMethodPolicyRequest request) {
        if (isEligible(request)) {
            return eligible();
        }
        return ineligible();
    }

    private boolean isEligible(final VerificationMethodPolicyRequest request) {
        final Collection<MobileDevice> mobileDevices = request.getMobileDevices();
        return mobileDevices.stream().anyMatch(MobileDevice::isTrusted);
    }

    private VerificationMethod ineligible() {
        return new MobilePinsentryIneligible(params.getFunction(), new NoMobileApplication());
    }

    private VerificationMethod eligible() {
        return new MobilePinsentryEligible(params);
    }

}
