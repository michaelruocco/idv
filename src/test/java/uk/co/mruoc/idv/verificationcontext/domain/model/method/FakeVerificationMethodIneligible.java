package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.ToString;

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

}
