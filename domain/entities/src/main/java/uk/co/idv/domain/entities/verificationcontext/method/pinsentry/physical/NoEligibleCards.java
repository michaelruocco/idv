package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

public class NoEligibleCards extends Ineligible {

    public NoEligibleCards() {
        super("no eligible cards found");
    }

}
