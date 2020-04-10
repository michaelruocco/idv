package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class RecordResultRequest {

    private final UUID contextId;
    private final VerificationResult result;

}
