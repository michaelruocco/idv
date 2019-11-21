package uk.co.idv.json.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

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
    void shouldReturnHardLockoutStateCalculatorForHardLockoutParameters() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.hardLock();

        final LockoutStateCalculator stateCalculator = factory.build(parameters);

        assertThat(stateCalculator).isInstanceOf(HardLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnStateCalculator() {
        final HardLockoutPolicyParameters parameters = LockoutPolicyParametersMother.hardLock();

        final HardLockoutStateCalculator stateCalculator = (HardLockoutStateCalculator) factory.build(parameters);

        assertThat(stateCalculator.getMaxNumberOfAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

}
