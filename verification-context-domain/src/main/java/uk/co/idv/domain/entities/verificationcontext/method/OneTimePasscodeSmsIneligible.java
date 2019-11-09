package uk.co.idv.domain.entities.verificationcontext.method;

public class OneTimePasscodeSmsIneligible extends AbstractVerificationMethodIneligible implements OneTimePasscodeSms {

    public OneTimePasscodeSmsIneligible() {
        super(NAME, new NoEligibleMobileNumbers());
    }

}
