package uk.co.idv.domain.entities.verificationcontext.method;

import java.time.Duration;

public interface VerificationMethodParams {

    int getMaxAttempts();

    Duration getDuration();

}
