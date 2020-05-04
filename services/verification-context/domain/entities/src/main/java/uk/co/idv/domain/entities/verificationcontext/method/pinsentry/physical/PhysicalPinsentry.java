package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;

public class PhysicalPinsentry extends DefaultVerificationMethod {

    public static final String NAME = "physical-pinsentry";

    private final PinsentryParams params;
    private final Collection<CardNumber> cardNumbers;

    public PhysicalPinsentry(final PinsentryParams params,
                             final Eligibility eligibility,
                             final VerificationResults results,
                             final Collection<CardNumber> cardNumbers) {
        super(NAME, params, eligibility, results);
        this.params = params;
        this.cardNumbers = cardNumbers;
    }

    public PinsentryFunction getFunction() {
        return params.getFunction();
    }

    public Collection<CardNumber> getCardNumbers() {
        return cardNumbers;
    }

}
