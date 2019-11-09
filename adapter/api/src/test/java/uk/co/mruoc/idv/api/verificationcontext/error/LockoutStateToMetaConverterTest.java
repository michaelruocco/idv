package uk.co.mruoc.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.FakeLockoutStateMaxAttemptsLocked;
import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.entities.lockout.LockoutStateMaxAttempts;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;

class LockoutStateToMetaConverterTest {

    @Test
    void shouldConvertMaxAttemptsLockoutStateToMetaData() {
        final LockoutStateMaxAttempts lockoutState = new FakeLockoutStateMaxAttemptsLocked();

        final Map<String, Object> meta = LockoutStateToMetaConverter.toMeta(lockoutState);

        assertThat(meta).containsOnly(
                entry("maxNumberOfAttempts", lockoutState.getMaxNumberOfAttempts()),
                entry("numberOfAttemptsRemaining", lockoutState.getNumberOfAttemptsRemaining()),
                entry("idvId", lockoutState.getIdvId())
        );
    }

    @Test
    void shouldLockoutStateToEmptyMetaData() {
        final LockoutState lockoutState = mock(LockoutState.class);

        final Map<String, Object> meta = LockoutStateToMetaConverter.toMeta(lockoutState);

        assertThat(meta).isEmpty();
    }

}
