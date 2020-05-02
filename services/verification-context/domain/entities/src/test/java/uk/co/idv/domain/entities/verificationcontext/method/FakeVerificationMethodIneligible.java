package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.FakeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

@ToString
public class FakeVerificationMethodIneligible extends AbstractVerificationMethodIneligible implements FakeVerificationMethod {

    public FakeVerificationMethodIneligible() {
        this(new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final String name) {
        super(name, new FakeIneligible());
    }

    public FakeVerificationMethodIneligible(final Ineligible ineligible) {
        super(NAME, ineligible);
    }

    @Override
    public VerificationMethod addResult(final VerificationResult result) {
        throw new CannotAddResultToIneligibleMethodException(getName());
    }

}
