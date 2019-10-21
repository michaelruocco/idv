package uk.co.mruoc.idv.mongo.lockout.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationAttemptsConverterTest {

    private final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
    private final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

    private final VerificationAttemptDocument document1 = mock(VerificationAttemptDocument.class);
    private final VerificationAttemptDocument document2 = mock(VerificationAttemptDocument.class);

    private final VerificationAttemptConverter attemptConverter = mock(VerificationAttemptConverter.class);

    private final VerificationAttemptsConverter attemptsConverter = new VerificationAttemptsConverter(attemptConverter);

    @Test
    void shouldConvertIdToDocument() {
        final VerificationAttempts attempts = new FakeVerificationAttempts();

        final VerificationAttemptsDocument documents = attemptsConverter.toAttemptsDocument(attempts);

        assertThat(documents.getId()).isEqualTo(attempts.getId().toString());
    }

    @Test
    void shouldConvertIdvIdToDocument() {
        final VerificationAttempts attempts = new FakeVerificationAttempts();

        final VerificationAttemptsDocument documents = attemptsConverter.toAttemptsDocument(attempts);

        assertThat(documents.getIdvId()).isEqualTo(attempts.getIdvId().toString());
    }

    @Test
    void shouldConvertAttemptsToDocuments() {
        given(attemptConverter.toDocument(attempt1)).willReturn(document1);
        given(attemptConverter.toDocument(attempt2)).willReturn(document2);
        final VerificationAttempts attempts = new FakeVerificationAttempts(Arrays.asList(attempt1, attempt2));

        final VerificationAttemptsDocument documents = attemptsConverter.toAttemptsDocument(attempts);

        assertThat(documents.getAttempts()).containsExactly(document1, document2);
    }

    @Test
    void shouldConvertIdToAttempts() {
        final VerificationAttemptsDocument document = VerificationAttemptDocumentMother.attemptsDocument();

        final VerificationAttempts attempts = attemptsConverter.toAttempts(document);

        assertThat(attempts.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldConvertIdvIdToAttempts() {
        final VerificationAttemptsDocument document = VerificationAttemptDocumentMother.attemptsDocument();

        final VerificationAttempts attempts = attemptsConverter.toAttempts(document);

        assertThat(attempts.getIdvId()).isEqualTo(UUID.fromString(document.getIdvId()));
    }

    @Test
    void shouldConvertDocumentsToAttempts() {
        given(attemptConverter.toAttempt(document1)).willReturn(attempt1);
        given(attemptConverter.toAttempt(document2)).willReturn(attempt2);
        final VerificationAttemptsDocument document = VerificationAttemptDocumentMother.attemptsDocument(
                document1,
                document2
        );

        final VerificationAttempts attempts = attemptsConverter.toAttempts(document);

        assertThat(attempts).containsExactly(attempt1, attempt2);
    }


}
