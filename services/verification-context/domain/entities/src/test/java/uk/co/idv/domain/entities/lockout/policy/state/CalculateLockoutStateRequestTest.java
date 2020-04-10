package uk.co.idv.domain.entities.lockout.policy.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest.CalculateLockoutStateRequestBuilder;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CalculateLockoutStateRequestTest {

    private final CalculateLockoutStateRequestBuilder builder = CalculateLockoutStateRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channelId";

        final CalculateLockoutStateRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activityName";

        final CalculateLockoutStateRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final CalculateLockoutStateRequest request = builder.timestamp(timestamp).build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final CalculateLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        final Alias alias = AliasesMother.creditCardNumber();

        final CalculateLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnIdvIdValue() {
        final UUID idvIdValue = UUID.randomUUID();

        final CalculateLockoutStateRequest request = builder.idvIdValue(idvIdValue).build();

        assertThat(request.getIdvIdValue()).isEqualTo(idvIdValue);
    }

    @Test
    void shouldReturnAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

        final CalculateLockoutStateRequest request = builder.attempts(attempts).build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldCopyChannelIdWhenUpdatingAttempts() {
        final String channelId = "channel-id";
        final CalculateLockoutStateRequest request = builder.channelId(channelId).build();

        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(VerificationAttemptsMother.oneAttempt());

        assertThat(updatedRequest.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldCopyActivityNameWhenUpdatingAttempts() {
        final String activityName = "activity-name";
        final CalculateLockoutStateRequest request = builder.activityName(activityName).build();

        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(VerificationAttemptsMother.oneAttempt());

        assertThat(updatedRequest.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldCopyTimestampWhenUpdatingAttempts() {
        final Instant timestamp = Instant.now();
        final CalculateLockoutStateRequest request = builder.timestamp(timestamp).build();

        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(VerificationAttemptsMother.oneAttempt());

        assertThat(updatedRequest.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldCopyAliasWhenUpdatingAttempts() {
        final Alias alias = AliasesMother.creditCardNumber();
        final CalculateLockoutStateRequest request = builder.alias(alias).build();

        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(VerificationAttemptsMother.oneAttempt());

        assertThat(updatedRequest.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldCopyIdvIdValueWhenUpdatingAttempts() {
        final UUID idvIdValue = UUID.randomUUID();
        final CalculateLockoutStateRequest request = builder.idvIdValue(idvIdValue).build();

        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(VerificationAttemptsMother.oneAttempt());

        assertThat(updatedRequest.getIdvIdValue()).isEqualTo(idvIdValue);
    }

    @Test
    void shouldUpdateAttempts() {
        final CalculateLockoutStateRequest request = builder.attempts(VerificationAttemptsMother.oneAttempt()).build();

        final VerificationAttempts updatedAttempts = VerificationAttemptsMother.withNumberOfAttempts(2);
        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(updatedAttempts);

        assertThat(updatedRequest.getAttempts()).isEqualTo(updatedAttempts);
    }

}
