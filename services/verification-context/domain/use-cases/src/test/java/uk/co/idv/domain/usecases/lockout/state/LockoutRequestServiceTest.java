package uk.co.idv.domain.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutRequestServiceTest {

    private final VerificationAttemptsLoader attemptsLoader = mock(VerificationAttemptsLoader.class);
    private final LockoutStateRequestConverter requestConverter = mock(LockoutStateRequestConverter.class);

    private final LockoutRequestService service = LockoutRequestService.builder()
            .requestConverter(requestConverter)
            .attemptsLoader(attemptsLoader)
            .build();

    @Test
    void shouldAddLoadedAttemptsToRequest() {
        final LockoutStateRequest request = LockoutStateRequestMother.build();
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        given(attemptsLoader.load(request.getIdvIdValue())).willReturn(attempts);
        final CalculateLockoutStateRequest expectedCalculateRequest = mock(CalculateLockoutStateRequest.class);
        given(requestConverter.toCalculateRequest(request, attempts)).willReturn(expectedCalculateRequest);

        final CalculateLockoutStateRequest calculateRequest = service.toCalculateRequest(request);

        assertThat(calculateRequest).isEqualTo(expectedCalculateRequest);
    }

}
