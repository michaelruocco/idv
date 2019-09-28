package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class RecordResultRequest {

    private final UUID contextId;
    private final VerificationResult result;

}
