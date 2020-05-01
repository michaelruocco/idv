package uk.co.idv.domain.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequestMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.attempt.FakeVerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.attempt.ResetAttemptsRequest;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateResetterTest {

    private final LockoutStateRequestConverter requestConverter = mock(LockoutStateRequestConverter.class);
    private final VerificationAttemptsLoader attemptsLoader = mock(VerificationAttemptsLoader.class);
    private final FakeVerificationAttemptDao dao = new FakeVerificationAttemptDao();
    private final LockoutPolicyService policyService = mock(LockoutPolicyService.class);

    private final LockoutStateResetter resetter = LockoutStateResetter.builder()
            .requestConverter(requestConverter)
            .attemptsLoader(attemptsLoader)
            .policyService(policyService)
            .dao(dao)
            .build();

    @Test
    void shouldSaveResetAttempts() {
        final ResetAttemptsRequest resetRequest = buildResetRequest();
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policyService.load(resetRequest)).willReturn(policy);

        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(2);
        given(attemptsLoader.load(resetRequest.getIdvIdValue())).willReturn(attempts);
        final CalculateLockoutStateRequest calculateRequest = CalculateLockoutStateRequestMother.withOneAttempt();
        given(requestConverter.toCalculateRequest(resetRequest, attempts)).willReturn(calculateRequest);

        final VerificationAttempts expectedResetAttempts = VerificationAttemptsMother.oneAttempt();
        given(policy.reset(calculateRequest)).willReturn(expectedResetAttempts);

        resetter.reset(resetRequest);

        final VerificationAttempts resetAttempts = dao.getLastSavedAttempts();
        assertThat(resetAttempts).isEqualTo(expectedResetAttempts);
    }

    private static ResetAttemptsRequest buildResetRequest() {
        return ResetAttemptsRequest.builder()
                .idvIdValue(UUID.randomUUID())
                .build();
    }

}
