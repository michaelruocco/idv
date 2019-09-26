package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class AbstractVerificationResult implements VerificationResult {

    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

}
