package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.Duration;

@Builder
@EqualsAndHashCode
public class DefaultPasscodeParams implements PasscodeParams {

    private final int length;
    private final Duration duration;
    private final int maxDeliveries;

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public int getMaxDeliveries() {
        return maxDeliveries;
    }

}
