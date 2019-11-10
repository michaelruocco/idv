package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;

public class OneTimePasscodeSmsIneligible extends AbstractVerificationMethodIneligible implements OneTimePasscodeSms {

    public OneTimePasscodeSmsIneligible() {
        super(NAME, new NoEligibleMobileNumbers());
    }

}
