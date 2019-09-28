package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

@Builder
public class VerificationContextResultRecorder {

    private final VerificationContextLoader contextLoader;
    private final LockoutService lockoutService;
    private final VerificationContextDao dao;

    public VerificationContext recordResult(final RecordResultRequest request) {
        final VerificationContext context = loadContext(request.getContextId());
        final VerificationResult result = request.getResult();
        final VerificationContext contextWithResult = context.addResult(result);
        recordAttempt(result, contextWithResult);
        dao.save(contextWithResult);
        return contextWithResult;
    }

    private VerificationContext loadContext(final UUID id) {
        final LoadContextRequest request = LoadContextRequest.builder()
                .id(id)
                .build();
        return contextLoader.load(request);
    }

    private void recordAttempt(final VerificationResult result, final VerificationContext context) {
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .result(result)
                .context(context)
                .build();
        lockoutService.recordAttempt(request);
    }

}
