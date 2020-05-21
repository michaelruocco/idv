package uk.co.idv.domain.entities.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;

public class FakeVerificationMethodEligible extends DefaultVerificationMethod implements FakeVerificationMethod {

    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final Eligibility ELIGIBLE = new Eligible();

    public FakeVerificationMethodEligible() {
        this(new DefaultVerificationResults());
    }

    public FakeVerificationMethodEligible(final String name) {
        this(name, new DefaultVerificationResults(), DURATION);
    }

    public FakeVerificationMethodEligible(final Duration duration) {
        this(NAME, new DefaultVerificationResults(), duration);
    }

    public FakeVerificationMethodEligible(final VerificationResult result) {
        this(result.getMethodName(), new DefaultVerificationResults(result), DURATION);
    }

    public FakeVerificationMethodEligible(final VerificationResults results) {
        this(NAME, results, DURATION);
    }

    public FakeVerificationMethodEligible(final String name, final VerificationResults results, final Duration duration) {
        super(name, toParams(duration), ELIGIBLE, results);
    }

    private static VerificationMethodParams toParams(final Duration duration) {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(2)
                .duration(duration)
                .build();
    }

}
