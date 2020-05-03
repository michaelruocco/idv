package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;

@Getter
@Builder
@EqualsAndHashCode
public class DefaultVerificationMethodParams implements VerificationMethodParams {

    private final int maxAttempts;
    private final Duration duration;

}
