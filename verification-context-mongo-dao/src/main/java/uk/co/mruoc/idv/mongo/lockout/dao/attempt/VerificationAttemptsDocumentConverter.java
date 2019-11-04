package uk.co.mruoc.idv.mongo.lockout.dao.attempt;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationAttemptsDocumentConverter {

    private final VerificationAttemptDocumentConverter attemptConverter;

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
                .map(attemptConverter::toAttempt)
                .collect(Collectors.toList());
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
                .map(attemptConverter::toDocument)
                .collect(Collectors.toList());
    }

}
