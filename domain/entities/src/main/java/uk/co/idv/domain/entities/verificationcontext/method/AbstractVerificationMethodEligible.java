package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractVerificationMethodEligible extends AbstractVerificationMethod {

    public AbstractVerificationMethodEligible(final String name,
                                              final VerificationResults results,
                                              final int maxAttempts,
                                              final Duration duration) {
        super(name, results, maxAttempts, duration, new Eligible());
    }

    @Override
    protected abstract VerificationMethod updateResults(final VerificationResults results);

}
