package uk.co.idv.domain.entities.verificationcontext.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class DefaultVerificationResult implements VerificationResult {

    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

}
