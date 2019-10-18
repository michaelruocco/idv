package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

public class StubbedSequenceLoader implements SequenceLoader {

    @Override
    public VerificationSequences loadSequences(final LoadSequencesRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (valueEndsWithNine(providedAlias)) {
            return new FakeVerificationSequencesIneligible();
        }
        return new FakeVerificationSequencesEligible();
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
