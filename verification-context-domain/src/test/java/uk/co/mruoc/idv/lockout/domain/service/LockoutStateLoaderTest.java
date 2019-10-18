package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
