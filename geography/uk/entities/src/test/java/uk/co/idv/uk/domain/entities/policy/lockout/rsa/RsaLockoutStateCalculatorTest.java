package uk.co.idv.uk.domain.entities.policy.lockout.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutStateCalculatorTest {

    private final HardLockoutStateCalculator calculator = new RsaLockoutStateCalculator();

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(calculator.getMaxNumberOfAttempts()).isEqualTo(3);
    }

}
