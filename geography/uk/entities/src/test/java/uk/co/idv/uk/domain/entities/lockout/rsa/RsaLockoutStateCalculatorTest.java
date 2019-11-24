package uk.co.idv.uk.domain.entities.lockout.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutStateCalculatorTest {

    private final HardLockoutStateCalculator stateCalculator = new RsaLockoutStateCalculator();

    @Test
    void shouldHaveMaximumNumberOfAttemptsOfThree() {
        assertThat(stateCalculator.getMaxNumberOfAttempts()).isEqualTo(3);
    }

}
