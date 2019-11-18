package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.FakeLockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutStateLoaderTest {

    private final FakeVerificationAttemptsLoader attemptsLoader = new FakeVerificationAttemptsLoader();
    private final FakeLockoutPolicyService policyService = new FakeLockoutPolicyService();

    private final LockoutStateLoader loader = LockoutStateLoader.builder()
            .attemptsLoader(attemptsLoader)
            .policyService(policyService)
            .requestConverter(new LockoutStateRequestConverter())
            .build();

    @Test
    void shouldPassRequestIdvIdValueWhenLoadingAttempts() {
        final LockoutStateRequest request = buildRequest();

        loader.load(request);

        assertThat(attemptsLoader.getLastLoadedIdvIdValue()).isEqualTo(request.getIdvIdValue());
    }

    @Test
    void shouldPassRequestWhenResettingAttempts() {
        final LockoutStateRequest request = buildRequest();
        attemptsLoader.setAttemptsToLoad(new FakeVerificationAttempts());

        loader.load(request);

        final CalculateLockoutStateRequest lastCalculateRequest = policyService.getLastCalculateRequest();
        assertThat(lastCalculateRequest).isEqualToIgnoringGivenFields(request, "attempts");
    }

    @Test
    void shouldPassLoadedAttemptsWhenResettingAttempts() {
        final LockoutStateRequest request = buildRequest();
        final VerificationAttempts attempts = new FakeVerificationAttempts();
        attemptsLoader.setAttemptsToLoad(attempts);

        loader.load(request);

        final CalculateLockoutStateRequest lastCalculateRequest = policyService.getLastCalculateRequest();
        assertThat(lastCalculateRequest.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnCalculatedLockoutState() {
        final LockoutStateRequest request = buildRequest();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        policyService.setStateToReturn(expectedState);

        final LockoutState state = loader.load(request);

        assertThat(state).isEqualTo(expectedState);
    }

    private static LockoutStateRequest buildRequest() {
        return DefaultLoadLockoutStateRequest.builder()
                .idvIdValue(UUID.randomUUID())
                .build();
    }

}
