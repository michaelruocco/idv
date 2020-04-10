package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

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
        return contextLoader.load(id);
    }

    private void recordAttempt(final VerificationResult result, final VerificationContext context) {
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .result(result)
                .context(context)
                .build();
        lockoutService.recordAttempt(request);
    }

}
