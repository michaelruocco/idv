package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationResultsConverterTest {

    private final VerificationResult result1 = mock(VerificationResult.class);
    private final VerificationResult result2 = mock(VerificationResult.class);

    private final VerificationResultDocument document1 = mock(VerificationResultDocument.class);
    private final VerificationResultDocument document2 = mock(VerificationResultDocument.class);

    private final VerificationResultConverter resultConverter = mock(VerificationResultConverter.class);

    private final VerificationResultsConverter resultsConverter = new VerificationResultsConverter(resultConverter);

    @Test
    void shouldConvertResultsToDocuments() {
        given(resultConverter.toDocument(result1)).willReturn(document1);
        given(resultConverter.toDocument(result2)).willReturn(document2);
        final VerificationResults results = new DefaultVerificationResults(result1, result2);

        final Collection<VerificationResultDocument> documents = resultsConverter.toDocuments(results);

        assertThat(documents).containsExactly(document1, document2);
    }

    @Test
    void shouldConvertDocumentsToResults() {
        given(resultConverter.toResult(document1)).willReturn(result1);
        given(resultConverter.toResult(document2)).willReturn(result2);
        final Collection<VerificationResultDocument> documents = Arrays.asList(document1, document2);

        final VerificationResults results = resultsConverter.toResults(documents);

        assertThat(results).containsExactly(result1, result2);
    }


}
