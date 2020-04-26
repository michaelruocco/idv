package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService.RequestedLockoutPolicyNotFoundException;


import static org.assertj.core.api.Assertions.assertThat;

class RequestedLockoutPolicyNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        final LockoutRequest request = VerificationAttemptsMother.successful();

        final Throwable exception = new RequestedLockoutPolicyNotFoundException(request);

        final String expectedMessage = String.format("channelId %s activity %s aliasType %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnRequest() {
        final LockoutRequest request = VerificationAttemptsMother.successful();

        final RequestedLockoutPolicyNotFoundException exception = new RequestedLockoutPolicyNotFoundException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
