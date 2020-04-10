package uk.co.idv.domain.entities.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.attempt.DefaultVerificationAttempt.DefaultVerificationAttemptBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultVerificationAttemptTest {

    private final DefaultVerificationAttemptBuilder builder = DefaultVerificationAttempt.builder();

    @Test
    void shouldBeSuccessful() {
        final VerificationAttempt attempt = builder.buildSuccessful();

        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeSuccessful() {
        final VerificationAttempt attempt = builder.buildFailed();

        assertThat(attempt.isSuccessful()).isFalse();
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
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = AliasesMother.creditCardNumber();

        final VerificationAttempt attempt = builder.alias(providedAlias).build();

        assertThat(attempt.getAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempt attempt = builder.idvIdValue(idvId).build();

        assertThat(attempt.getIdvIdValue()).isEqualTo(idvId);
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
