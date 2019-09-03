package uk.co.mruoc.verificationcontext.domain;

import java.time.Duration;

public class DefaultPasscodeSettings implements PasscodeSettings {

    @Override
    public int getLength() {
        return 8;
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMillis(150000);
    }

    @Override
    public int getMaxAttempts() {
        return 3;
    }

}
