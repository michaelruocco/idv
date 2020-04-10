package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;

public class OneTimePasscodeIneligible extends AbstractVerificationMethodIneligible implements OneTimePasscode {

    public OneTimePasscodeIneligible() {
        super(NAME, new NoEligibleDeliveryMethods());
    }

}
