package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams.DefaultPinsentryParamsBuilder;

import java.time.Duration;

public class PinsentryParamsMother {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    public static PinsentryParams eligible() {
        return builder().build();
    }

    public static PinsentryParams ineligible(final PinsentryFunction function) {
        return new IneligiblePinsentryParams(function);
    }

    public static PinsentryParams withMaxAttempts(int maxAttempts) {
        return builder()
                .maxAttempts(maxAttempts)
                .build();
    }

    public static DefaultPinsentryParamsBuilder builder() {
        return DefaultPinsentryParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .function(FUNCTION);
    }

}
