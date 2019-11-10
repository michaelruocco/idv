package uk.co.idv.domain.entities.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParametersMother;
import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateCalculatorFactoryTest {

    private final LockoutStateCalculatorFactory factory = new LockoutStateCalculatorFactory();

    @Test
    void shouldThrowLockoutTypeNotSupportedExceptionForInvalidLockoutType() {
        final DefaultLockoutPolicyParameters parameters = mock(DefaultLockoutPolicyParameters.class);
        given(parameters.getLockoutType()).willReturn("invalid");

        final Throwable error = catchThrowable(() -> factory.build(parameters));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage(parameters.getLockoutType());
    }

    @Test
    void shouldReturnMaxAttemptsLockoutStateCalculatorForMaxAttemptsParameters() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutStateCalculator stateCalculator = factory.build(parameters);

        assertThat(stateCalculator).isInstanceOf(MaxAttemptsLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnStateCalculator() {
        final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final MaxAttemptsLockoutStateCalculator stateCalculator = (MaxAttemptsLockoutStateCalculator) factory.build(parameters);

        assertThat(stateCalculator.getMaxAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

}
