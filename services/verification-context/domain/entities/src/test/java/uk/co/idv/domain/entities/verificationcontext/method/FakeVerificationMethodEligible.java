package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;

@ToString
@EqualsAndHashCode(callSuper = true)
public class FakeVerificationMethodEligible extends AbstractVerificationMethodEligible implements FakeVerificationMethod {

    private static final int MAX_ATTEMPTS = 2;
    private static final Duration DURATION = Duration.ofMinutes(5);

    public FakeVerificationMethodEligible() {
        this(new DefaultVerificationResults());
    }

    public FakeVerificationMethodEligible(final String name) {
        super(name, new DefaultVerificationResults(), MAX_ATTEMPTS, DURATION);
    }

    public FakeVerificationMethodEligible(final Duration duration) {
        super(NAME, new DefaultVerificationResults(), MAX_ATTEMPTS, duration);
    }

    public FakeVerificationMethodEligible(final VerificationResult result) {
        this(result.getMethodName(), new DefaultVerificationResults(result));
    }

    public FakeVerificationMethodEligible(final VerificationResults results) {
        this(NAME, results);
    }

    public FakeVerificationMethodEligible(final String name, final VerificationResults results) {
        super(name, results, MAX_ATTEMPTS, DURATION);
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new FakeVerificationMethodEligible(getName(), results);
    }

}
