package uk.co.idv.domain.entities.verificationcontext.method.params;

import lombok.EqualsAndHashCode;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

@EqualsAndHashCode
public class IneligibleVerificationMethodParams implements VerificationMethodParams {

    @Override
    public int getMaxAttempts() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

}
