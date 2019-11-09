package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationSequencesIneligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

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
