package uk.co.mruoc.idv.lockout.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.lockout.domain.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.VerificationAttemptSuccessful;
import uk.co.mruoc.idv.lockout.domain.VerificationAttemptSuccessful.VerificationAttemptSuccessfulBuilder;

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
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = new FakeCreditCardNumber();

        final VerificationAttempt attempt = builder.providedAlias(providedAlias).build();

        assertThat(attempt.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempt attempt = builder.idvId(idvId).build();

        assertThat(attempt.getIdvId()).isEqualTo(idvId);
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
