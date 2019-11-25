package uk.co.idv.api.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NonLockingPolicyAttributesConverterTest {

    private final LockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.nonLocking();

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);

    private final LockoutPolicyAttributesConverter converter = new NonLockingPolicyAttributesConverter(recordAttemptStrategyFactory);

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

        assertThat(policy.getStateCalculator()).isInstanceOf(NonLockingLockoutStateCalculator.class);
    }

}
