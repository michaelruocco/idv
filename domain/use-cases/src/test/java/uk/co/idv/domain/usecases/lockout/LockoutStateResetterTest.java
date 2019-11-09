package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.FakeVerificationAttemptFailed;
import uk.co.idv.domain.entities.lockout.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutStateResetterTest {

    private final FakeVerificationAttemptsLoader attemptsLoader = new FakeVerificationAttemptsLoader();
    private final FakeVerificationAttemptsDao dao = new FakeVerificationAttemptsDao();
    private final FakeLockoutPolicyService policyService = new FakeLockoutPolicyService();

    private final LockoutStateResetter resetter = LockoutStateResetter.builder()
            .requestConverter(new LockoutStateRequestConverter())
            .attemptsLoader(attemptsLoader)
            .policyService(policyService)
            .dao(dao)
            .build();

    @Test
    void shouldPassRequestIdvIdValueWhenLoadingAttempts() {
        final LockoutStateRequest request = buildResetRequest();

        resetter.reset(request);

        assertThat(attemptsLoader.getLastLoadedIdvIdValue()).isEqualTo(request.getIdvIdValue());
    }

    @Test
    void shouldPassRequestWhenResettingAttempts() {
        final ResetAttemptsRequest resetRequest = buildResetRequest();
        attemptsLoader.setAttemptsToLoad(new FakeVerificationAttempts());

        resetter.reset(resetRequest);

        final CalculateLockoutStateRequest lastResetRequest = policyService.getLastResetRequest();
        assertThat(lastResetRequest).isEqualToIgnoringGivenFields(resetRequest, "attempts");
    }

    @Test
    void shouldPassLoadedAttemptsWhenResettingAttempts() {
        final ResetAttemptsRequest resetRequest = buildResetRequest();
        final VerificationAttempts attempts = new FakeVerificationAttempts();
        attemptsLoader.setAttemptsToLoad(attempts);

        resetter.reset(resetRequest);

        final CalculateLockoutStateRequest lastResetRequest = policyService.getLastResetRequest();
        assertThat(lastResetRequest.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldSaveResetAttempts() {
        final ResetAttemptsRequest resetRequest = buildResetRequest();
        final VerificationAttempts attempts = new FakeVerificationAttempts(
                new FakeVerificationAttemptFailed(),
                new FakeVerificationAttemptFailed()
        );
        attemptsLoader.setAttemptsToLoad(attempts);
        final VerificationAttempts expectedResetAttempts = new FakeVerificationAttempts(new FakeVerificationAttemptFailed());
        policyService.setResetAttemptsToReturn(expectedResetAttempts);

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
