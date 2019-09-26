package uk.co.mruoc.idv.verificationcontext.domain.model.method;

public class CardCredentialsIneligible extends AbstractVerificationMethodIneligible implements CardCredentials {

    public CardCredentialsIneligible() {
        super(NAME, new NoEligibleCards());
    }

}
