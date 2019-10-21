package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResultSuccessful;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultConverterTest {

    private final VerificationResultConverter converter = new VerificationResultConverter();

    @Test
    void shouldPopulateSuccessfulOnDocument() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationResultDocument document = converter.toDocument(result);

        assertThat(document.isSuccessful()).isEqualTo(result.isSuccessful());
    }

    @Test
    void shouldPopulateMethodNameOnDocument() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationResultDocument document = converter.toDocument(result);

        assertThat(document.getMethodName()).isEqualTo(result.getMethodName());
    }

    @Test
    void shouldPopulateTimestampOnDocument() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationResultDocument document = converter.toDocument(result);

        assertThat(document.getTimestamp()).isEqualTo(result.getTimestamp().toString());
    }

    @Test
    void shouldPopulateVerificationIdOnDocument() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final VerificationResultDocument document = converter.toDocument(result);

        assertThat(document.getVerificationId()).isEqualTo(result.getVerificationId().toString());
    }

    @Test
    void shouldConvertToSuccessfulResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.successful();

        final VerificationResult result = converter.toResult(document);

        assertThat(result).isInstanceOf(VerificationResultSuccessful.class);
    }

    @Test
    void shouldPopulateMethodNameOnSuccessfulResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.successful();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getMethodName()).isEqualTo(document.getMethodName());
    }

    @Test
    void shouldPopulateTimestampOnSuccessfulResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.successful();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

    @Test
    void shouldPopulateVerificationIdOnSuccessfulResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.successful();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getVerificationId()).isEqualTo(UUID.fromString(document.getVerificationId()));
    }

    @Test
    void shouldConvertToFailedResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.failed();

        final VerificationResult result = converter.toResult(document);

        assertThat(result).isInstanceOf(VerificationResultFailed.class);
    }

    @Test
    void shouldPopulateMethodNameOnFailedResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.failed();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getMethodName()).isEqualTo(document.getMethodName());
    }

    @Test
    void shouldPopulateTimestampOnFailedResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.failed();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

    @Test
    void shouldPopulateVerificationIdOnFailedResult() {
        final VerificationResultDocument document = VerificationResultDocumentMother.failed();

        final VerificationResult result = converter.toResult(document);

        assertThat(result.getVerificationId()).isEqualTo(UUID.fromString(document.getVerificationId()));
    }

}
