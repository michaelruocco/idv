package uk.co.idv.domain.entities.verificationcontext.method.pinsentry;

import lombok.EqualsAndHashCode;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
public class IneligiblePinsentryParams extends PinsentryParams {

    public IneligiblePinsentryParams(final PinsentryFunction function) {
        super(0, Duration.ZERO, function);
    }

}
