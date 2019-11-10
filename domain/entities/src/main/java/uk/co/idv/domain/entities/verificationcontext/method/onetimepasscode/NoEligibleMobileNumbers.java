package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

public class NoEligibleMobileNumbers extends Ineligible {

    public NoEligibleMobileNumbers() {
        super("no eligible mobile numbers found");
    }

}
