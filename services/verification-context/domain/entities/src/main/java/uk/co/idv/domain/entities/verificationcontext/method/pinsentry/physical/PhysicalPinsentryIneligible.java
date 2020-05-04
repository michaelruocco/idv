package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

import java.util.Collection;

public class PhysicalPinsentryIneligible extends PhysicalPinsentry {

    public PhysicalPinsentryIneligible(final PinsentryFunction function,
                                       final Ineligible reason,
                                       final Collection<CardNumber> cardNumbers) {
        super(
                new IneligiblePinsentryParams(function),
                reason,
                new DefaultVerificationResults(),
                cardNumbers
        );
    }

}
