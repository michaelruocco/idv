package uk.co.idv.repository.mongo.verificationcontext.result;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultSuccessful;

import java.time.Instant;
import java.util.UUID;

public class VerificationResultDocumentConverter {

    public VerificationResult toResult(final VerificationResultDocument document) {
        if (document.isSuccessful()) {
            return toSuccessfulResult(document);
        }
        return toFailedResult(document);
    }

    public VerificationResultDocument toDocument(final VerificationResult result) {
        final VerificationResultDocument document = new VerificationResultDocument();
        document.setSuccessful(result.isSuccessful());
        document.setMethodName(result.getMethodName());
        document.setTimestamp(result.getTimestamp().toString());
        document.setVerificationId(result.getVerificationId().toString());
        return document;
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
