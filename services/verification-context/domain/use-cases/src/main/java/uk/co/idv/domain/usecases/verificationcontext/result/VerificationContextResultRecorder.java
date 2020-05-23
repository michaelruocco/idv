package uk.co.idv.domain.usecases.verificationcontext.result;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

public interface VerificationContextResultRecorder {

    VerificationContext recordResult(RecordResultRequest request);

}
