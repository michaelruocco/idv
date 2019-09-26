package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

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
