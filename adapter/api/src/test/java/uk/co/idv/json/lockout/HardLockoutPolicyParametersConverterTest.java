package uk.co.idv.json.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HardLockoutPolicyParametersConverterTest {

    private final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.hardLock();

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);

    private final LockoutPolicyParametersConverter converter = new HardLockoutPolicyParametersConverter(recordAttemptStrategyFactory);

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getLockoutLevel()).isEqualTo(parameters.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(parameters.getRecordAttempts())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getStateCalculator()).isInstanceOf(HardLockoutStateCalculator.class);
    }

}
