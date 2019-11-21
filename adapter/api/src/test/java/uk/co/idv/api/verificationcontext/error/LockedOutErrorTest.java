package uk.co.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockedLockoutState;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutState;
import uk.co.mruoc.jsonapi.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LockedOutErrorTest {

    private static final HardLockoutState LOCKOUT_STATE_MAX_ATTEMPTS = new FakeHardLockedLockoutState();

    private final ApiError error = new LockedOutError(LOCKOUT_STATE_MAX_ATTEMPTS);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Locked");
    }

    @Test
    void shouldReturnDetail() {
        assertThat(error.getDetail()).isEqualTo(LOCKOUT_STATE_MAX_ATTEMPTS.getMessage());
    }

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(423);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).containsOnly(
                entry("maxNumberOfAttempts", LOCKOUT_STATE_MAX_ATTEMPTS.getMaxNumberOfAttempts()),
                entry("numberOfAttemptsRemaining", LOCKOUT_STATE_MAX_ATTEMPTS.getNumberOfAttemptsRemaining()),
                entry("idvId", LOCKOUT_STATE_MAX_ATTEMPTS.getIdvId())
        );
    }

}
