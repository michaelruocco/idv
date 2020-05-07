package uk.co.idv.domain.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.usecases.policy.PolicyService.RequestedPolicyNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestedPolicyNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        final LockoutRequest request = VerificationAttemptsMother.successful();

        final Throwable exception = new RequestedPolicyNotFoundException(request);

        final String expectedMessage = String.format("channelId %s activity %s aliasType %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnRequest() {
        final LockoutRequest request = VerificationAttemptsMother.successful();

        final RequestedPolicyNotFoundException exception = new RequestedPolicyNotFoundException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
