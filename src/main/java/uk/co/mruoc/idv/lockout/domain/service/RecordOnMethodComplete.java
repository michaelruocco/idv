package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

public class RecordOnMethodComplete implements RecordAttemptStrategy {

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        final VerificationContext context = request.getContext();
        final VerificationResult result = request.getResult();
        return context.containsCompleteMethod(result.getMethodName());
    }

}