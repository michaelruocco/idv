package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;

class RecordAttemptRequestConverterTest {

    private static final String METHOD_NAME = "method-name";

    private final VerificationContext context = VerificationContextMother.fake();

    private final RecordAttemptRequestConverter converter = new RecordAttemptRequestConverter();

    @Test
    void shouldConvertSuccessfulResultToSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    void shouldPopulateMethodNameOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getMethodName()).isEqualTo(result.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getVerificationId()).isEqualTo(result.getVerificationId());
    }

    @Test
    void shouldPopulateTimestampOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getTimestamp()).isEqualTo(result.getTimestamp());
    }

    @Test
    void shouldPopulateContextIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateChannelIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPopulateProvidedAliasOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPopulateIdvIdOnSuccessfulAttempt() {
        final VerificationResult result = new FakeVerificationResultSuccessful(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    @Test
    void shouldConvertFailedResultToFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.isSuccessful()).isFalse();
    }


    @Test
    void shouldPopulateMethodNameOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getMethodName()).isEqualTo(result.getMethodName());
    }

    @Test
    void shouldPopulateVerificationIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getVerificationId()).isEqualTo(result.getVerificationId());
    }

    @Test
    void shouldPopulateTimestampOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getTimestamp()).isEqualTo(result.getTimestamp());
    }

    @Test
    void shouldPopulateContextIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateChannelIdOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPopulateProvidedAliasOnFailedAttempt() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPopulateIdvIdOnSuccessfulFailed() {
        final VerificationResult result = new FakeVerificationResultFailed(METHOD_NAME);
        final RecordAttemptRequest request = toRequest(result);

        final VerificationAttempt attempt = converter.toAttempt(request);

        assertThat(attempt.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    private RecordAttemptRequest toRequest(final VerificationResult result) {
        return RecordAttemptRequest.builder()
                .result(result)
                .context(context)
                .build();
    }

}
