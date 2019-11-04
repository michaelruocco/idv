package uk.co.mruoc.idv.mongo.lockout.dao.attempt;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttemptSuccessful;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptSuccessful;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocumentConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocumentMother;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationAttemptDocumentConverterTest {

    private final AliasDocumentConverter aliasConverter = mock(AliasDocumentConverter.class);

    private final VerificationAttemptDocumentConverter converter = new VerificationAttemptDocumentConverter(aliasConverter);

    @Test
    void shouldPopulateContextIdOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getContextId()).isEqualTo(attempt.getContextId().toString());
    }

    @Test
    void shouldPopulateChannelIdOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getChannelId()).isEqualTo(attempt.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getActivityName()).isEqualTo(attempt.getActivityName());
    }

    @Test
    void shouldPopulateAliasOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();
        final AliasDocument aliasDocument = AliasDocumentMother.creditCard();
        given(aliasConverter.toDocument(attempt.getAlias())).willReturn(aliasDocument);

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getAlias()).isEqualTo(aliasDocument);
    }

    @Test
    void shouldPopulateIdvIdValueOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getIdvIdValue()).isEqualTo(attempt.getIdvIdValue().toString());
    }

    @Test
    void shouldPopulateMethodNameOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getMethodName()).isEqualTo(attempt.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getVerificationId()).isEqualTo(attempt.getVerificationId().toString());
    }

    @Test
    void shouldPopulateTimestampOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.getTimestamp()).isEqualTo(attempt.getTimestamp().toString());
    }

    @Test
    void shouldPopulateSuccessfulOnDocument() {
        final VerificationAttempt attempt = new FakeVerificationAttemptSuccessful();

        final VerificationAttemptDocument document = converter.toDocument(attempt);

        assertThat(document.isSuccessful()).isEqualTo(attempt.isSuccessful());
    }

    @Test
    void shouldConvertToSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt).isInstanceOf(VerificationAttemptSuccessful.class);
    }

    @Test
    void shouldPopulateContextIdOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getContextId()).isEqualTo(UUID.fromString(document.getContextId()));
    }

    @Test
    void shouldPopulateChannelIdOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getChannelId()).isEqualTo(document.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getActivityName()).isEqualTo(document.getActivityName());
    }

    @Test
    void shouldPopulateAliasOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();
        final Alias alias = AliasesMother.creditCardNumber();
        given(aliasConverter.toAlias(document.getAlias())).willReturn(alias);

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPopulateIdvIdValueOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getIdvIdValue()).isEqualTo(UUID.fromString(document.getIdvIdValue()));
    }

    @Test
    void shouldPopulateMethodNameOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getMethodName()).isEqualTo(document.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getVerificationId()).isEqualTo(UUID.fromString(document.getVerificationId()));
    }

    @Test
    void shouldPopulateTimestampOnSuccessfulAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.successful();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

    @Test
    void shouldConvertToFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt).isInstanceOf(VerificationAttemptFailed.class);
    }

    @Test
    void shouldPopulateContextIdOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getContextId()).isEqualTo(UUID.fromString(document.getContextId()));
    }

    @Test
    void shouldPopulateChannelIdOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getChannelId()).isEqualTo(document.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getActivityName()).isEqualTo(document.getActivityName());
    }

    @Test
    void shouldPopulateAliasOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();
        final Alias alias = AliasesMother.creditCardNumber();
        given(aliasConverter.toAlias(document.getAlias())).willReturn(alias);

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPopulateIdvIdValueOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getIdvIdValue()).isEqualTo(UUID.fromString(document.getIdvIdValue()));
    }

    @Test
    void shouldPopulateMethodNameOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getMethodName()).isEqualTo(document.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getVerificationId()).isEqualTo(UUID.fromString(document.getVerificationId()));
    }

    @Test
    void shouldPopulateTimestampOnFailedAttempt() {
        final VerificationAttemptDocument document = VerificationAttemptDocumentMother.failed();

        final VerificationAttempt attempt = converter.toAttempt(document);

        assertThat(attempt.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

}
