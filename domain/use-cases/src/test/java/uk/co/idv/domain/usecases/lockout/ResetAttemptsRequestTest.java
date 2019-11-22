package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.usecases.lockout.ResetAttemptsRequest.ResetAttemptsRequestBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResetAttemptsRequestTest {

    private final ResetAttemptsRequestBuilder builder = ResetAttemptsRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channel-id";

        final ResetAttemptsRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activity-name";

        final ResetAttemptsRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final ResetAttemptsRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        final Alias alias = AliasesMother.creditCardNumber();

        final ResetAttemptsRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final ResetAttemptsRequest request = builder.timestamp(timestamp).build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnIdvIdValue() {
        final UUID idvIdValue = UUID.randomUUID();

        final ResetAttemptsRequest request = builder.idvIdValue(idvIdValue).build();

        assertThat(request.getIdvIdValue()).isEqualTo(idvIdValue);
    }

    @Test
    void shouldReturnAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

        final ResetAttemptsRequest request = builder.attempts(attempts).build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

}
