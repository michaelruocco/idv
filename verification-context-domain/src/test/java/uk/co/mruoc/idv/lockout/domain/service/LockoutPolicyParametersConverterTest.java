package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculator;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory;
import uk.co.mruoc.idv.lockout.domain.model.RecordAttemptStrategy;
import uk.co.mruoc.idv.lockout.domain.model.RecordAttemptStrategyFactory;

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
    void shouldPopulateParametersOnPolicy() {
        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getParameters()).isEqualTo(parameters);
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
