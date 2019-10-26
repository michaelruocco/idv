package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttemptSuccessful.VerificationAttemptSuccessfulBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationAttemptSuccessfulTest {

    private final VerificationAttemptSuccessfulBuilder builder = VerificationAttemptSuccessful.builder();

    @Test
    void shouldBeSuccessful() {
        final VerificationAttempt attempt = builder.build();

        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final VerificationAttempt attempt = builder.contextId(contextId).build();

        assertThat(attempt.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channel-id";

        final VerificationAttempt attempt = builder.channelId(channelId).build();

        assertThat(attempt.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activity-name";

        final VerificationAttempt attempt = builder.activityName(activityName).build();

        assertThat(attempt.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final VerificationAttempt attempt = builder.alias(alias).build();

        assertThat(attempt.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnIdvId() {
        final UUID idvIdValue = UUID.randomUUID();

        final VerificationAttempt attempt = builder.idvIdValue(idvIdValue).build();

        assertThat(attempt.getIdvIdValue()).isEqualTo(idvIdValue);
    }

    @Test
    void shouldReturnMethodName() {
        final String methodName = "method-name";

        final VerificationAttempt attempt = builder.methodName(methodName).build();

        assertThat(attempt.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnVerificationId() {
        final UUID verificationId = UUID.randomUUID();

        final VerificationAttempt attempt = builder.verificationId(verificationId).build();

        assertThat(attempt.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final VerificationAttempt attempt = builder.timestamp(timestamp).build();

        assertThat(attempt.getTimestamp()).isEqualTo(timestamp);
    }

}
