package uk.co.idv.repository.mongo.verificationcontext.result;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationResultsDocumentConverter {

    private final VerificationResultDocumentConverter resultConverter;

    public VerificationResults toResults(final Collection<VerificationResultDocument> documents) {
        final Collection<VerificationResult> results = documents.stream()
                .map(resultConverter::toResult)
                .collect(Collectors.toList());
        return new DefaultVerificationResults(results);
    }

    public Collection<VerificationResultDocument> toDocuments(final VerificationResults results) {
        return results.stream()
                .map(resultConverter::toDocument)
                .collect(Collectors.toList());
    }

}
