package uk.co.idv.domain.usecases.verificationcontext.result;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

@Builder
public class VerificationContextResultRecorder {

    private final VerificationContextLoader contextLoader;
    private final LockoutService lockoutService;
    private final VerificationContextDao dao;

    public VerificationContext recordResult(final RecordResultRequest request) {
        final VerificationContext context = loadContext(request.getContextId());
        final VerificationResult result = request.getResult();
        context.addResult(result);
        recordAttempt(result, context);
        dao.save(context);
        return context;
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
