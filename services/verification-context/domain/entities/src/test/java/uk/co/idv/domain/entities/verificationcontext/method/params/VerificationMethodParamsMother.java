package uk.co.idv.domain.entities.verificationcontext.method.params;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams.DefaultVerificationMethodParamsBuilder;

import java.time.Duration;

public class VerificationMethodParamsMother {

    public static VerificationMethodParams eligible() {
        return builder().build();
    }

    public static DefaultVerificationMethodParamsBuilder builder() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(5)
                .duration(Duration.ofMinutes(5));
    }

    public static VerificationMethodParams ineligible() {
        return new IneligibleVerificationMethodParams();
    }

}
