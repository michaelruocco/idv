package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptFailed;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultConverterTest {

    private static final String METHOD_NAME = "method-name";

    private final VerificationContext context = new FakeVerificationContext();

    private final VerificationResultConverter converter = new VerificationResultConverter();

    @Test
    void shouldConvertSuccessfulResultToSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt).isInstanceOf(VerificationAttemptSuccessful.class);
        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    void shouldPopulateMethodNameOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getMethodName()).isEqualTo(result.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getVerificationId()).isEqualTo(result.getVerificationId());
    }

    @Test
    void shouldPopulateTimestampOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getTimestamp()).isEqualTo(result.getTimestamp());
    }

    @Test
    void shouldPopulateContextIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateChannelIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPopulateProvidedAliasOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getProvidedAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPopulateIdvIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    @Test
    void shouldConvertFailedResultToFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt).isInstanceOf(VerificationAttemptFailed.class);
        assertThat(attempt.isSuccessful()).isFalse();
    }


    @Test
    void shouldPopulateMethodNameOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getMethodName()).isEqualTo(result.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getVerificationId()).isEqualTo(result.getVerificationId());
    }

    @Test
    void shouldPopulateTimestampOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getTimestamp()).isEqualTo(result.getTimestamp());
    }

    @Test
    void shouldPopulateContextIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateChannelIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPopulateProvidedAliasOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getProvidedAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPopulateIdvIdOnSuccessfulFailed() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);

        final VerificationAttempt attempt = converter.toAttempt(result, context);

        assertThat(attempt.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

}