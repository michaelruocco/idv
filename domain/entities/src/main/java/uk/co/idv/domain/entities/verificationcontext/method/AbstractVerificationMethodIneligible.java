package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
        throw new VerificationMethod.CannotAddResultToIneligibleMethodException(getName());
    }

}
