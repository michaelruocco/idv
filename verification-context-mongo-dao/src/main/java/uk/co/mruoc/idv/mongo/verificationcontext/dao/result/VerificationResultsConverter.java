package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationResultsConverter {

    private final VerificationResultConverter resultConverter;

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
