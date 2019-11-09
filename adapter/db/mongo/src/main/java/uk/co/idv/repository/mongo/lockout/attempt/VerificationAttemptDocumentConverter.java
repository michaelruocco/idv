package uk.co.idv.repository.mongo.lockout.attempt;

import lombok.RequiredArgsConstructor;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentConverter;
import uk.co.idv.domain.entities.lockout.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.VerificationAttemptFailed;
import uk.co.idv.domain.entities.lockout.VerificationAttemptSuccessful;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class VerificationAttemptDocumentConverter {

    private final AliasDocumentConverter aliasConverter;

    public VerificationAttempt toAttempt(final VerificationAttemptDocument document) {
        if (document.isSuccessful()) {
            return toSuccessfulAttempt(document);
        }
        return toFailedAttempt(document);
    }

    public VerificationAttemptDocument toDocument(final VerificationAttempt attempt) {
        final VerificationAttemptDocument document = new VerificationAttemptDocument();
        document.setContextId(attempt.getContextId().toString());
        document.setChannelId(attempt.getChannelId());
        document.setActivityName(attempt.getActivityName());
        document.setAlias(aliasConverter.toDocument(attempt.getAlias()));
        document.setIdvIdValue(attempt.getIdvIdValue().toString());
        document.setMethodName(attempt.getMethodName());
        document.setVerificationId(attempt.getVerificationId().toString());
        document.setTimestamp(attempt.getTimestamp().toString());
        document.setSuccessful(attempt.isSuccessful());
        return document;
    }

    private VerificationAttempt toSuccessfulAttempt(final VerificationAttemptDocument document) {
        return VerificationAttemptSuccessful.builder()
                .contextId(UUID.fromString(document.getContextId()))
                .channelId(document.getChannelId())
                .activityName(document.getActivityName())
                .alias(aliasConverter.toAlias(document.getAlias()))
                .idvIdValue(UUID.fromString(document.getIdvIdValue()))
                .methodName(document.getMethodName())
                .verificationId(UUID.fromString(document.getVerificationId()))
                .timestamp(Instant.parse(document.getTimestamp()))
                .build();
    }

    private VerificationAttempt toFailedAttempt(final VerificationAttemptDocument document) {
        return VerificationAttemptFailed.builder()
                .contextId(UUID.fromString(document.getContextId()))
                .channelId(document.getChannelId())
                .activityName(document.getActivityName())
                .alias(aliasConverter.toAlias(document.getAlias()))
                .idvIdValue(UUID.fromString(document.getIdvIdValue()))
                .methodName(document.getMethodName())
                .verificationId(UUID.fromString(document.getVerificationId()))
                .timestamp(Instant.parse(document.getTimestamp()))
                .build();
    }

}
