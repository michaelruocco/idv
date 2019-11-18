package uk.co.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.FakeMaxAttemptsLockedLockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutState;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;

class LockoutStateToMetaConverterTest {

    @Test
    void shouldConvertMaxAttemptsLockoutStateToMetaData() {
        final MaxAttemptsLockoutState lockoutState = new FakeMaxAttemptsLockedLockoutState();

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
