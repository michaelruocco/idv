package uk.co.idv.domain.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.policy.FakeLockoutPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateLoaderTest {

    private final FakeLockoutPolicyService policyService = new FakeLockoutPolicyService();
    private final LockoutRequestService requestService = mock(LockoutRequestService.class);

    private final LockoutStateLoader loader = LockoutStateLoader.builder()
            .policyService(policyService)
            .requestService(requestService)
            .build();

    @Test
    void shouldReturnCalculatedLockoutState() {
        final LockoutStateRequest request = LockoutStateRequestMother.build();
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        policyService.setPolicyToLoad(policy);

        final CalculateLockoutStateRequest calculateRequest = mock(CalculateLockoutStateRequest.class);
        given(requestService.toCalculateRequest(request)).willReturn(calculateRequest);

        final LockoutState expectedState = mock(LockoutState.class);
        given(policy.calculateState(calculateRequest)).willReturn(expectedState);

        final LockoutState state = loader.load(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
