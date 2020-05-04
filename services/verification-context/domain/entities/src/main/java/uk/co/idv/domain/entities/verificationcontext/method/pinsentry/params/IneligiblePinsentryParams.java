package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@EqualsAndHashCode
public class IneligiblePinsentryParams implements PinsentryParams {

    private final PinsentryFunction function;

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

    @Override
    public int getMaxAttempts() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

}
