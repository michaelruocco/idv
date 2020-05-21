package uk.co.idv.domain.entities.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.FakeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

import java.time.Duration;

public class FakeVerificationMethodIneligible extends DefaultVerificationMethod implements FakeVerificationMethod {

    public FakeVerificationMethodIneligible() {
        this(NAME, new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final String name) {
        this(name, new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final String name, final Ineligible ineligible) {
        super(name, params(), ineligible, new DefaultVerificationResults());
    }

    private static VerificationMethodParams params() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(0)
                .duration(Duration.ZERO)
                .build();
    }

}
