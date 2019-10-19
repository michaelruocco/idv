package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class VerificationResultConverter {

    public VerificationResults toResults(final Collection<VerificationResultDocument> documents) {
        final Collection<VerificationResult> results = documents.stream()
                .map(this::toResult)
                .collect(Collectors.toList());
        return new DefaultVerificationResults(results);
    }

    public VerificationResult toResult(final VerificationResultDocument document) {
        if (document.isSuccessful()) {
            return toSuccessfulResult(document);
        }
        return toFailedResult(document);
    }

    public Collection<VerificationResultDocument> toDocuments(final VerificationResults results) {
        return results.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public VerificationResultDocument toDocument(final VerificationResult result) {
        return VerificationResultDocument.builder()
                .successful(result.isSuccessful())
                .methodName(result.getMethodName())
                .timestamp(result.getTimestamp().toString())
                .verificationId(result.getVerificationId().toString())
                .build();
    }

    private VerificationResult toSuccessfulResult(final VerificationResultDocument document) {
        return VerificationResultSuccessful.builder()
                .methodName(document.getMethodName())
                .timestamp(Instant.parse(document.getTimestamp()))
                .verificationId(UUID.fromString(document.getVerificationId()))
                .build();
    }

    private VerificationResult toFailedResult(final VerificationResultDocument document) {
        return VerificationResultFailed.builder()
                .methodName(document.getMethodName())
                .timestamp(Instant.parse(document.getTimestamp()))
                .verificationId(UUID.fromString(document.getVerificationId()))
                .build();
    }

}
