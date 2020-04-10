package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

public class NoEligibleDeliveryMethods extends Ineligible {

    public NoEligibleDeliveryMethods() {
        super("no eligible delivery methods found");
    }

}
