package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;

public class PhysicalPinsentryEligible extends PhysicalPinsentry {

    public PhysicalPinsentryEligible(final PinsentryParams params,
                                     final Collection<CardNumber> cardNumbers) {
        this(params, new DefaultVerificationResults(), cardNumbers);
    }

    @Builder
    public PhysicalPinsentryEligible(final PinsentryParams params,
                                     final VerificationResults results,
                                     final Collection<CardNumber> cardNumbers) {
        super(params, new Eligible(), results, cardNumbers);
    }

}
