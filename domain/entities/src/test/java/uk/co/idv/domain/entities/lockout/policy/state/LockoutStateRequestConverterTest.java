package uk.co.idv.domain.entities.lockout.policy.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttemptSuccessful;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LockoutStateRequestConverterTest {

    private final VerificationAttempts attempts = mock(VerificationAttempts.class);

    private final LockoutStateRequestConverter converter = new LockoutStateRequestConverter();

    @Test
    void shouldPopulateChannelIdFromRequest() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getChannelId()).isEqualTo(lockoutRequest.getChannelId());
    }

    @Test
    void shouldPopulateActivityNameFromRequest() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getActivityName()).isEqualTo(lockoutRequest.getActivityName());
    }

    @Test
    void shouldPopulateAliasFromRequest() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getAlias()).isEqualTo(lockoutRequest.getAlias());
    }

    @Test
    void shouldPopulateTimestampFromRequest() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getTimestamp()).isEqualTo(lockoutRequest.getTimestamp());
    }

    @Test
    void shouldPopulateIdvIdValueFromRequest() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getIdvIdValue()).isEqualTo(lockoutRequest.getIdvIdValue());
    }

    @Test
    void shouldPopulateAttempts() {
        final LockoutStateRequest lockoutRequest = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final CalculateLockoutStateRequest calculateRequest = converter.toCalculateRequest(lockoutRequest, attempts);

        assertThat(calculateRequest.getAttempts()).isEqualTo(attempts);
    }

}
