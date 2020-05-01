package uk.co.idv.domain.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.attempt.FakeVerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.policy.FakeLockoutPolicyService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateLoaderTest {

    private final FakeVerificationAttemptsLoader attemptsLoader = new FakeVerificationAttemptsLoader();
    private final FakeLockoutPolicyService policyService = new FakeLockoutPolicyService();
    private final LockoutStateRequestConverter requestConverter = mock(LockoutStateRequestConverter.class);

    private final LockoutStateLoader loader = LockoutStateLoader.builder()
            .attemptsLoader(attemptsLoader)
            .policyService(policyService)
            .requestConverter(requestConverter)
            .build();

    @Test
    void shouldReturnCalculatedLockoutState() {
        final LockoutStateRequest request = buildRequest();
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        policyService.setPolicyToLoad(policy);

        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        attemptsLoader.setAttemptsToLoad(attempts);

        final CalculateLockoutStateRequest calculateRequest = mock(CalculateLockoutStateRequest.class);
        given(requestConverter.toCalculateRequest(request, attempts)).willReturn(calculateRequest);

        final LockoutState expectedState = mock(LockoutState.class);
        given(policy.calculateState(calculateRequest)).willReturn(expectedState);

        final LockoutState state = loader.load(request);

        assertThat(state).isEqualTo(expectedState);
    }

    private static LockoutStateRequest buildRequest() {
        return DefaultLoadLockoutStateRequest.builder()
                .idvIdValue(UUID.randomUUID())
                .build();
    }

}
