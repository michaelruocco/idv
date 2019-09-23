package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.EqualsAndHashCode;

import java.time.Duration;

@EqualsAndHashCode
public class IneligiblePasscodeSettings implements PasscodeSettings {

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    public int getMaxAttempts() {
        return 0;
    }

}
