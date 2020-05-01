package uk.co.idv.domain.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequestMother;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.attempt.FakeVerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateResetterTest {

    private final LockoutRequestService requestService = mock(LockoutRequestService.class);
    private final FakeVerificationAttemptDao dao = new FakeVerificationAttemptDao();
    private final LockoutPolicyService policyService = mock(LockoutPolicyService.class);

    private final LockoutStateResetter resetter = LockoutStateResetter.builder()
            .requestService(requestService)
            .policyService(policyService)
            .dao(dao)
            .build();

    @Test
    void shouldSaveResetAttempts() {
        final LockoutStateRequest resetRequest = LockoutStateRequestMother.build();
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policyService.load(resetRequest)).willReturn(policy);

        final CalculateLockoutStateRequest calculateRequest = CalculateLockoutStateRequestMother.withOneAttempt();
        given(requestService.toCalculateRequest(resetRequest)).willReturn(calculateRequest);

        final VerificationAttempts expectedResetAttempts = VerificationAttemptsMother.oneAttempt();
        given(policy.reset(calculateRequest)).willReturn(expectedResetAttempts);

        resetter.reset(resetRequest);

        final VerificationAttempts resetAttempts = dao.getLastSavedAttempts();
        assertThat(resetAttempts).isEqualTo(expectedResetAttempts);
    }

}
