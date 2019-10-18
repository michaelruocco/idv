package uk.co.mruoc.idv.verificationcontext.domain.model.method;

public class OneTimePasscodeSmsIneligible extends AbstractVerificationMethodIneligible implements OneTimePasscodeSms {

    public OneTimePasscodeSmsIneligible() {
        super(NAME, new NoEligibleMobileNumbers());
    }

}
