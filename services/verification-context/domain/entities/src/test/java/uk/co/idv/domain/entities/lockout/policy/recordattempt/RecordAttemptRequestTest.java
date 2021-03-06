package uk.co.idv.domain.entities.lockout.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest.RecordAttemptRequestBuilder;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;

class RecordAttemptRequestTest {

    private final RecordAttemptRequestBuilder builder = RecordAttemptRequest.builder();

    @Test
    void shouldReturnResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final RecordAttemptRequest request = builder.result(result).build();

        assertThat(request.getResult()).isEqualTo(result);
    }

    @Test
    void shouldReturnTimestampFromResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

        final RecordAttemptRequest request = builder.result(result).build();

        assertThat(request.getTimestamp()).isEqualTo(result.getTimestamp());
    }

    @Test
    void shouldReturnContext() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getContext()).isEqualTo(context);
    }

    @Test
    void shouldReturnIdvIdValueFromContext() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    @Test
    void shouldReturnActivityNameFromContext() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldReturnAliasAsContextProvidedAlias() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldReturnAliasTypeFromContextProvidedAlias() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        final Alias providedAlias = context.getProvidedAlias();
        assertThat(request.getAliasType()).isEqualTo(providedAlias.getType());
    }

    @Test
    void shouldReturnChannelIdFromContext() {
        final VerificationContext context = VerificationContextMother.fake();

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getChannelId()).isEqualTo(context.getChannelId());
    }

}
