package uk.co.idv.domain.entities.verificationcontext.method.params;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

public class DefaultVerificationMethodParamsMother {

    public static VerificationMethodParams eligible() {
        return withDuration(Duration.ofMinutes(5));
    }

    public static VerificationMethodParams withDuration(final Duration duration) {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(5)
                .duration(duration)
                .build();
    }

    public static VerificationMethodParams ineligible() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(0)
                .duration(Duration.ZERO)
                .build();
    }

}
