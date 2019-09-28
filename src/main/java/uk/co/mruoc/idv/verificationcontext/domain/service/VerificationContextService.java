package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

public interface VerificationContextService {

    VerificationContext create(final CreateContextRequest request);

    VerificationContext load(final LoadContextRequest request);

    VerificationContext recordResult(final RecordResultRequest request);

}
