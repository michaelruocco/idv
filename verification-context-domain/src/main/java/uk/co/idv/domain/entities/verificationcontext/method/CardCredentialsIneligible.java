package uk.co.idv.domain.entities.verificationcontext.method;

public class CardCredentialsIneligible extends AbstractVerificationMethodIneligible implements CardCredentials {

    public CardCredentialsIneligible() {
        super(NAME, new NoEligibleCards());
    }

}
