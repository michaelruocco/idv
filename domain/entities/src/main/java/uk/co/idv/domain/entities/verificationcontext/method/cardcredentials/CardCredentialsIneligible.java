package uk.co.idv.domain.entities.verificationcontext.method.cardcredentials;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;

public class CardCredentialsIneligible extends AbstractVerificationMethodIneligible implements CardCredentials {

    public CardCredentialsIneligible() {
        super(NAME, new NoEligibleCards());
    }

}
