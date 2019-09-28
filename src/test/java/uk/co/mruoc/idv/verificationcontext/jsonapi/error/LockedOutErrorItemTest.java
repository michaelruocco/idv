package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttemptsLocked;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateMaxAttempts;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LockedOutErrorItemTest {

    private static final LockoutStateMaxAttempts LOCKOUT_STATE_MAX_ATTEMPTS = new FakeLockoutStateMaxAttemptsLocked();

    private final JsonApiErrorItem error = new LockedOutErrorItem(LOCKOUT_STATE_MAX_ATTEMPTS);

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
                entry("lockoutType", LOCKOUT_STATE_MAX_ATTEMPTS.getType()),
                entry("idvId", LOCKOUT_STATE_MAX_ATTEMPTS.getIdvId())
        );
    }

}
