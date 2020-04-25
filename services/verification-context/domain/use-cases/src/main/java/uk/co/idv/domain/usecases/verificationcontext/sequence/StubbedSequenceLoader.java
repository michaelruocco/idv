package uk.co.idv.domain.usecases.verificationcontext.sequence;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesIneligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

public class StubbedSequenceLoader implements SequenceLoader {

    @Override
    public VerificationSequences loadSequences(final LoadSequencesRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (valueEndsWithNine(providedAlias)) {
            return new StubVerificationSequencesIneligible();
        }
        return new StubVerificationSequencesEligible();
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
