package uk.co.mruoc.idv.lockout.domain.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockoutStateCalculatorMaxAttemptsThree extends LockoutStateCalculatorMaxAttempts {

    private static final int MAX_ATTEMPTS = 3;

    public LockoutStateCalculatorMaxAttemptsThree() {
        super(MAX_ATTEMPTS);
    }

}
