package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersMother;

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
        final MaxAttemptsLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final MaxAttemptsLockoutStateCalculator stateCalculator = (MaxAttemptsLockoutStateCalculator) factory.build(parameters);

        assertThat(stateCalculator.getMaxAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

}
