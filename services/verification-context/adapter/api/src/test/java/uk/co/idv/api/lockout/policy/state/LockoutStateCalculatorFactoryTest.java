package uk.co.idv.api.lockout.policy.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.state.LockoutStateCalculatorFactory;
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
        final DefaultLockoutPolicyAttributes parameters = mock(DefaultLockoutPolicyAttributes.class);
        given(parameters.getType()).willReturn("invalid");

        final Throwable error = catchThrowable(() -> factory.build(parameters));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage(parameters.getType());
    }

    @Test
    void shouldReturnHardLockoutStateCalculatorForHardLockoutParameters() {
        final DefaultLockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.hardLock();

        final LockoutStateCalculator stateCalculator = factory.build(parameters);

        assertThat(stateCalculator).isInstanceOf(HardLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnStateCalculator() {
        final HardLockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.hardLock();

        final HardLockoutStateCalculator stateCalculator = (HardLockoutStateCalculator) factory.build(parameters);

        assertThat(stateCalculator.getMaxNumberOfAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

}
