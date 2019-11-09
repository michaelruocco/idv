package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.usecases.lockout.RecordAttemptRequest;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

public class RecordOnMethodComplete implements RecordAttemptStrategy {

    public static final String TYPE = "on-method-complete";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        final VerificationContext context = request.getContext();
        final VerificationResult result = request.getResult();
        return context.containsCompleteMethod(result.getMethodName());
    }

}
