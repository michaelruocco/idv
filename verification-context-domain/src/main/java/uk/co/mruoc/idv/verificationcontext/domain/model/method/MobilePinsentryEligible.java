package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

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
