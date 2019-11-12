package uk.co.idv.json.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyParametersConverterTest {

    private final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final LockoutStateCalculatorFactory lockoutStateCalculatorFactory = mock(LockoutStateCalculatorFactory.class);

    private final LockoutPolicyParametersConverter converter = LockoutPolicyParametersConverter.builder()
            .recordAttemptStrategyFactory(recordAttemptStrategyFactory)
            .lockoutStateCalculatorFactory(lockoutStateCalculatorFactory)
            .build();

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getLockoutLevel()).isEqualTo(parameters.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(parameters.getRecordAttemptStrategyType())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);
        given(lockoutStateCalculatorFactory.build(parameters)).willReturn(stateCalculator);

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getStateCalculator()).isEqualTo(stateCalculator);
    }

}
