package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;

import java.time.Duration;

@EqualsAndHashCode
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
    public int getMaxDeliveryAttempts() {
        return 3;
    }

}
