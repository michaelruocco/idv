package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;

public class OneTimePasscodePolicyMother {

    public static OneTimePasscodePolicy build() {
        return new OneTimePasscodePolicy(OneTimePasscodeParamsMother.eligible());
    }

}
