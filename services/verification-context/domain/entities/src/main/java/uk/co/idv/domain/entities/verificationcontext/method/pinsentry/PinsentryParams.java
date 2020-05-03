package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

import java.time.Duration;

@Getter
@Builder
@EqualsAndHashCode
public class PinsentryParams implements VerificationMethodParams {

    private final int maxAttempts;
    private final Duration duration;
    private final PinsentryFunction function;

}
