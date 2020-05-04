package uk.co.idv.domain.entities.verificationcontext.method.params;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class DefaultVerificationMethodParams implements VerificationMethodParams {

    private final int maxAttempts;
    private final Duration duration;

}
