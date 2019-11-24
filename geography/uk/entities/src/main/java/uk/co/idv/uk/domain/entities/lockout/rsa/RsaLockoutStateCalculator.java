package uk.co.idv.uk.domain.entities.lockout.rsa;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

public class RsaLockoutStateCalculator extends HardLockoutStateCalculator {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public RsaLockoutStateCalculator() {
        super(MAX_NUMBER_OF_ATTEMPTS);
    }

}
