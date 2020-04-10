package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Duration;

@EqualsAndHashCode
@ToString
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
