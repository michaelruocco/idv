package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class DefaultPinsentryParams implements PinsentryParams {

    private final int maxAttempts;
    private final Duration duration;
    private final PinsentryFunction function;

}
