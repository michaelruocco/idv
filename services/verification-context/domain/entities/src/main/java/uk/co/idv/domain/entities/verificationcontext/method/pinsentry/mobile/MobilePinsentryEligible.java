package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;

public class MobilePinsentryEligible extends AbstractVerificationMethodEligible implements MobilePinsentry {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PinsentryFunction function;

    public MobilePinsentryEligible(final PinsentryFunction function) {
        this(function, new DefaultVerificationResults());
    }

    public MobilePinsentryEligible(final PinsentryFunction function, final VerificationResult result) {
        this(function, new DefaultVerificationResults(result));
    }

    public MobilePinsentryEligible(final PinsentryFunction function, final VerificationResults results) {
        super(NAME, results, MAX_ATTEMPTS, DURATION);
        this.function = function;
    }

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new MobilePinsentryEligible(function, results);
    }

}
