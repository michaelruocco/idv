package uk.co.mruoc.idv.lockout.service;

import uk.co.mruoc.idv.lockout.domain.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.VerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.VerificationAttemptSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

public class VerificationResultConverter {

    public VerificationAttempt toAttempt(final VerificationResult result, final VerificationContext context) {
        if (result.isSuccessful()) {
            return toSuccessfulAttempt(result, context);
        }
        return toFailedAttempt(result, context);
    }

    private VerificationAttempt toSuccessfulAttempt(final VerificationResult result, final VerificationContext context) {
        return VerificationAttemptSuccessful.builder()
                .methodName(result.getMethodName())
                .verificationId(result.getVerificationId())
                .timestamp(result.getTimestamp())
                .contextId(context.getId())
                .channelId(context.getChannelId())
                .activityName(context.getActivityName())
                .providedAlias(context.getProvidedAlias())
                .idvId(context.getIdvIdValue())
                .build();
    }

    private VerificationAttempt toFailedAttempt(final VerificationResult result, final VerificationContext context) {
        return VerificationAttemptFailed.builder()
                .methodName(result.getMethodName())
                .verificationId(result.getVerificationId())
                .timestamp(result.getTimestamp())
                .contextId(context.getId())
                .channelId(context.getChannelId())
                .activityName(context.getActivityName())
                .providedAlias(context.getProvidedAlias())
                .idvId(context.getIdvIdValue())
                .build();
    }

}
