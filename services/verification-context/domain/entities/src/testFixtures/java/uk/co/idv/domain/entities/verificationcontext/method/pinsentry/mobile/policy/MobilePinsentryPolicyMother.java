package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;

public class MobilePinsentryPolicyMother {

    public static MobilePinsentryPolicy build() {
        return new MobilePinsentryPolicy(PinsentryParamsMother.eligible());
    }

}
