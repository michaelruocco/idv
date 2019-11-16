package uk.co.idv.uk.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutStateCalculatorTest {

    private final MaxAttemptsLockoutStateCalculator stateCalculator = new RsaLockoutStateCalculator();

    @Test
    void shouldHaveMaximumNumberOfAttemptsOfThree() {
        assertThat(stateCalculator.getMaxAttempts()).isEqualTo(3);
    }

}
