package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptSuccessful;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class VerificationAttemptConverter {

    private final AliasConverter aliasConverter;

    public VerificationAttempts toAttempts(final VerificationAttemptsDocument document) {
        final Collection<VerificationAttempt> attempts = toAttempts(document.getAttempts());
        return new VerificationAttempts(
                UUID.fromString(document.getId()),
                UUID.fromString(document.getIdvId()),
                attempts
        );
    }

    public Collection<VerificationAttempt> toAttempts(final Collection<VerificationAttemptDocument> documents) {
        return documents.stream()
                .map(this::toAttempt)
                .collect(Collectors.toList());
    }

    public VerificationAttempt toAttempt(final VerificationAttemptDocument document) {
        if (document.isSuccessful()) {
            return toSuccessfulAttempt(document);
        }
        return toFailedAttempt(document);
    }

    public VerificationAttemptsDocument toAttemptsDocument(final VerificationAttempts attempts) {
       final Collection<VerificationAttemptDocument> attemptDocuments = toAttemptDocuments(attempts.asCollection());
       final VerificationAttemptsDocument attemptsDocument = new VerificationAttemptsDocument();
       attemptsDocument.setId(attempts.getId().toString());
       attemptsDocument.setIdvId(attempts.getIdvId().toString());
       attemptsDocument.setAttempts(attemptDocuments);
       return attemptsDocument;
    }

    public Collection<VerificationAttemptDocument> toAttemptDocuments(final Collection<VerificationAttempt> attempts) {
        return attempts.stream()
                .map(this::toAttemptDocument)
                .collect(Collectors.toList());
    }

    public VerificationAttemptDocument toAttemptDocument(final VerificationAttempt attempt) {
        final VerificationAttemptDocument document = new VerificationAttemptDocument();
        document.setContextId(attempt.getContextId().toString());
        document.setChannelId(attempt.getChannelId());
        document.setActivityName(attempt.getActivityName());
        document.setAlias(aliasConverter.toDocument(attempt.getAlias()));
        document.setIdvIdValue(attempt.getIdvIdValue().toString());
        document.setMethodName(attempt.getMethodName());
        document.setVerificationId(attempt.getVerificationId().toString());
        document.setTimestamp(attempt.getTimestamp().toString());
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
