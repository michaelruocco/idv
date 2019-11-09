package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.FakeVerificationAttemptSuccessful;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService.LockoutPolicyNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        final LockoutRequest request = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final Throwable exception = new LockoutPolicyNotFoundException(request);

        final String expectedMessage = String.format("channelId %s activity %s aliasType %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnRequest() {
        final LockoutRequest request = new FakeVerificationAttemptSuccessful(UUID.randomUUID());

        final LockoutPolicyNotFoundException exception = new LockoutPolicyNotFoundException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
