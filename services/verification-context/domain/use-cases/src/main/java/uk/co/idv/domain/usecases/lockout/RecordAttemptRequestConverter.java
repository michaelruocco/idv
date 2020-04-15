package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.attempt.DefaultVerificationAttempt;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

public class RecordAttemptRequestConverter {

    public VerificationAttempt toAttempt(final RecordAttemptRequest request) {
        return toAttempt(request.getResult(), request.getContext());
    }

    private VerificationAttempt toAttempt(final VerificationResult result, final VerificationContext context) {
        if (result.isSuccessful()) {
            return toSuccessfulAttempt(result, context);
        }
        return toFailedAttempt(result, context);
    }

    private VerificationAttempt toSuccessfulAttempt(final VerificationResult result, final VerificationContext context) {
        return toBuilder(result, context).buildSuccessful();
    }

    private VerificationAttempt toFailedAttempt(final VerificationResult result, final VerificationContext context) {
        return toBuilder(result, context).buildFailed();
    }

    private DefaultVerificationAttempt.DefaultVerificationAttemptBuilder toBuilder(final VerificationResult result,
                                                                                   final VerificationContext context) {
        return DefaultVerificationAttempt.builder()
                .methodName(result.getMethodName())
                .verificationId(result.getVerificationId())
                .timestamp(result.getTimestamp())
                .contextId(context.getId())
                .channelId(context.getChannelId())
                .activityName(context.getActivityName())
                .alias(context.getProvidedAlias())
                .idvIdValue(context.getIdvIdValue());
    }

}
