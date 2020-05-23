package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

public interface VerificationContextService extends VerificationContextLoader, VerificationContextResultRecorder {

    VerificationContext create(final CreateContextRequest request);

}
