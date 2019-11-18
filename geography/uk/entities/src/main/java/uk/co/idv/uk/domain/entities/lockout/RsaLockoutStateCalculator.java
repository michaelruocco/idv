package uk.co.idv.uk.domain.entities.lockout;

import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutStateCalculator;

public class RsaLockoutStateCalculator extends MaxAttemptsLockoutStateCalculator {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public RsaLockoutStateCalculator() {
        super(MAX_NUMBER_OF_ATTEMPTS);
    }

}
