package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class PhysicalPinsentryEligible extends AbstractVerificationMethodEligible implements PhysicalPinsentry {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PinsentryFunction function;
    private final Collection<CardNumber> cardNumbers;

    @Builder
    public PhysicalPinsentryEligible(final PinsentryFunction function, final Collection<CardNumber> cardNumbers) {
        this(function, cardNumbers, new DefaultVerificationResults());
    }

    public PhysicalPinsentryEligible(final PinsentryFunction function,
                                     final Collection<CardNumber> cardNumbers,
                                     final VerificationResult result) {
        this(function, cardNumbers, new DefaultVerificationResults(result));
    }

    public PhysicalPinsentryEligible(final PinsentryFunction function,
                                     final Collection<CardNumber> cardNumbers,
                                     final VerificationResults results) {
        super(NAME, results, MAX_ATTEMPTS, DURATION);
        this.function = function;
        this.cardNumbers = cardNumbers;
    }

    public Collection<CardNumber> getCardNumbers() {
        return Collections.unmodifiableCollection(cardNumbers);
    }

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

    @Override
    protected VerificationMethod updateResults(VerificationResults results) {
        return new PhysicalPinsentryEligible(function, cardNumbers, results);
    }

}
