package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

public interface VerificationContextService {

    VerificationContext create(final CreateContextRequest request);

    VerificationContext load(final LoadContextRequest request);

    VerificationContext recordResult(final RecordResultRequest request);

}
