package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;

public class PhysicalPinsentryPolicyMother {

    public static PhysicalPinsentryPolicy build() {
        return new PhysicalPinsentryPolicy(PinsentryParamsMother.eligible());
    }

}
