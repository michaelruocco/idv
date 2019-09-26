package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;

public abstract class AbstractVerificationMethodIneligible extends AbstractVerificationMethod {

    private static final VerificationResults RESULTS = new VerificationResultsAlwaysEmpty();
    private static final int MAX_ATTEMPTS = 0;
    private static final Duration DURATION = Duration.ZERO;

    public AbstractVerificationMethodIneligible(final String name, final Ineligible ineligible) {
        super(name, RESULTS, MAX_ATTEMPTS, DURATION, ineligible);
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        throw new UnsupportedOperationException("cannot update results on ineligible method");
    }

}
