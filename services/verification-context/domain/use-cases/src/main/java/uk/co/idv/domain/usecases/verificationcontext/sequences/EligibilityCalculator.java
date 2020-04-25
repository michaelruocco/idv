package uk.co.idv.domain.usecases.verificationcontext.sequences;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.LoadSequencesRequest;

public interface EligibilityCalculator {

    VerificationMethod calculate(final LoadSequencesRequest request);

}
