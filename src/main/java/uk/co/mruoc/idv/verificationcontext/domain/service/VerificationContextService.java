package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

public interface VerificationContextService {

    VerificationContext create(final CreateContextRequest request);

    VerificationContext get(final GetContextRequest request);

    VerificationContext updateResult(final UpdateContextResultRequest request);

}
