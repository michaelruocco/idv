package uk.co.mruoc.idv.lockout.domain.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaxAttemptsThreeLockoutStateCalculator extends MaxAttemptsLockoutStateCalculator {

    private static final int MAX_ATTEMPTS = 3;

    public MaxAttemptsThreeLockoutStateCalculator() {
        super(MAX_ATTEMPTS);
    }

}
